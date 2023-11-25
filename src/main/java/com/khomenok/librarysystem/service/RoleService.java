package com.khomenok.librarysystem.service;

import com.khomenok.librarysystem.model.entity.Role;
import com.khomenok.librarysystem.model.enums.RoleName;

public interface RoleService {
    void seedRoles();

    Role findByName(RoleName name);
}
