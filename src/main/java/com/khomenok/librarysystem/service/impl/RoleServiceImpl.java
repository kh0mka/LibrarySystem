package com.khomenok.librarysystem.service.impl;

import com.khomenok.librarysystem.model.entity.Role;
import com.khomenok.librarysystem.model.enums.RoleName;
import com.khomenok.librarysystem.repository.RoleRepository;
import com.khomenok.librarysystem.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void seedRoles() {
        if (this.roleRepository.count() == 0) {
            List<Role> roles = Arrays.stream(RoleName.values())
                    .map(Role::new)
                    .collect(Collectors.toList());
            this.roleRepository.saveAll(roles);
        }
    }

    @Override
    public Role findByName(RoleName name) {
       return this.roleRepository.findByName(name);
    }
}
