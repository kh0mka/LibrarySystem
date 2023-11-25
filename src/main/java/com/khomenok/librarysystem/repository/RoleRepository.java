package com.khomenok.librarysystem.repository;

import com.khomenok.librarysystem.model.entity.Role;
import com.khomenok.librarysystem.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName name);
}
