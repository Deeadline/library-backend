package com.yoko.libraryproject.service;

import com.yoko.libraryproject.dto.JwtResponse;
import com.yoko.libraryproject.dto.LoginDto;
import com.yoko.libraryproject.dto.MessageResponse;
import com.yoko.libraryproject.dto.RegisterDto;
import com.yoko.libraryproject.entity.Role;
import com.yoko.libraryproject.entity.RoleEnum;
import com.yoko.libraryproject.entity.User;
import com.yoko.libraryproject.exception.EmailAlreadyExistException;
import com.yoko.libraryproject.exception.RoleNotFoundException;
import com.yoko.libraryproject.exception.UserAlreadyExistException;
import com.yoko.libraryproject.repository.RoleRepository;
import com.yoko.libraryproject.repository.UserRepository;
import com.yoko.libraryproject.security.jwt.JwtUtils;
import com.yoko.libraryproject.security.service.UserDetailsImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {

    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder encoder;
    final JwtUtils jwtUtils;

    public UserServiceImplementation(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder encoder,
            JwtUtils jwtUtils
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    public JwtResponse login(LoginDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImplementation userDetails = (UserDetailsImplementation) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    public ResponseEntity<?> register(RegisterDto registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UserAlreadyExistException(registerRequest.getUsername());
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailAlreadyExistException(registerRequest.getUsername());
        }

        User user = createUser(registerRequest);
        user.setRoles(setRoles());
        userRepository.save(user);

        return ResponseEntity
                .ok(new MessageResponse("User registered successfully!"));
    }

    private Set<Role> setRoles() {
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                .orElseThrow(RoleNotFoundException::new);
        roles.add(userRole);
        return roles;
    }

    private User createUser(RegisterDto request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        return user;
    }
}
