package com.yoko.libraryproject.repository;

import com.yoko.libraryproject.entity.Role;
import com.yoko.libraryproject.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}
