package com.yoko.libraryproject.service;

import com.yoko.libraryproject.dto.JwtResponse;
import com.yoko.libraryproject.dto.LoginDto;
import com.yoko.libraryproject.dto.RegisterDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    JwtResponse login(LoginDto loginRequest);

    ResponseEntity<?> register(RegisterDto registerRequest);
}
