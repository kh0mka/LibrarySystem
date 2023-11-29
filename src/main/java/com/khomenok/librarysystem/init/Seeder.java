package com.khomenok.librarysystem.init;

import com.khomenok.librarysystem.model.entity.Role;
import com.khomenok.librarysystem.model.entity.User;
import com.khomenok.librarysystem.model.enums.RoleName;
import com.khomenok.librarysystem.repository.UserRepository;
import com.khomenok.librarysystem.service.CategoryService;
import com.khomenok.librarysystem.service.RoleService;
import com.khomenok.librarysystem.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
@Component
public class Seeder implements CommandLineRunner {
    private CategoryService categoryService;
    private RoleService roleService;
    private UserService userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    public Seeder(CategoryService categoryService, RoleService roleService,
                  UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.categoryService = categoryService;
        this.roleService = roleService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        this.categoryService.seedCategories();
        this.roleService.seedRoles();

        seedAdmin();
    }

    private void seedAdmin() {
        if (this.userRepository.count() > 0) {
            return;
        }
        Role userRole = this.roleService.findByName(RoleName.USER);
        Role adminRole = this.roleService.findByName(RoleName.ADMIN);

        User admin = new User("Admin", "User",
                "admin@java.com", passwordEncoder.encode("123321"));
        admin.getRoles().add(userRole);
        admin.getRoles().add(adminRole);
        this.userService.saveUser(admin);
    }
}
