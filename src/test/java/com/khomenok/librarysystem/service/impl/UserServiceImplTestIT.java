package com.khomenok.librarysystem.service.impl;

import com.khomenok.librarysystem.errors.ObjectNotFoundException;
import com.khomenok.librarysystem.model.dto.UserRegisterDTO;
import com.khomenok.librarysystem.model.entity.Role;
import com.khomenok.librarysystem.model.entity.User;
import com.khomenok.librarysystem.model.enums.RoleName;
import com.khomenok.librarysystem.repository.UserRepository;
import com.khomenok.librarysystem.service.RoleService;
import com.khomenok.librarysystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserServiceImplTestIT {

    @Autowired
    private UserService serviceToTest;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user;

    private User admin;

    @BeforeEach
    void setUp() {
        this.userRepository.deleteAll();

        Role userRole = this.roleService.findByName(RoleName.USER);
        Role adminRole = this.roleService.findByName(RoleName.ADMIN);

        user = new User("userFirstName", "userLastName", "userEmail",
                "userPassword");
        user.getRoles().add(userRole);
        admin = new User("adminFirstName", "adminLastName", "adminEmail",
                "adminPassword");
        admin.setRoles(Set.of(userRole, adminRole));
        this.userRepository.save(user);
        this.userRepository.save(admin);
    }

    @Test
    void testRegisterUser() {
        UserRegisterDTO registerDTO =
                new UserRegisterDTO("email", "firstName", "lastName",
                        "password", "password");

        assertEquals(2, this.userRepository.count());

        this.serviceToTest.register(registerDTO);

        User user = this.userRepository.findByEmail("email").orElse(null);
        List<User> users = this.userRepository.findAll();
        assertNotNull(user);
        assertEquals(3, users.size());
        assertEquals("firstName", user.getFirstName());
        assertEquals("lastName", user.getLastName());
        assertTrue(passwordEncoder.matches("password", user.getPassword()));
        assertEquals(1, user.getRoles().size());
        assertEquals("USER", user.getRoles().stream().findFirst().get().getName().name());

    }

    @Test
    void testGetUserByUsername() {
        User user = this.serviceToTest.getUser("userEmail");
        assertEquals("userEmail", user.getEmail());
        assertEquals("userFirstName", user.getFirstName());
        assertEquals("userLastName", user.getLastName());
        assertEquals("userPassword", user.getPassword());
        assertEquals(1, user.getRoles().size());
        assertEquals("USER", user.getRoles().stream().findFirst().get().getName().name());
    }

    @Test
    void testGetByUsernameNotExisting() {

        assertThrows(ObjectNotFoundException.class,
                () -> this.serviceToTest.getUser("notExistingEmail"));
        try {
            this.serviceToTest.getUser("notExistingEmail");
        } catch (ObjectNotFoundException exception) {
            assertEquals("user with email notExistingEmail not found",
                    exception.getMessage());
        }
    }

    @Test
    void testGetUserById() {
        User userFromDB = this.serviceToTest.getUser(user.getId());
        assertEquals("userEmail", user.getEmail());
        assertEquals("userFirstName", user.getFirstName());
        assertEquals("userLastName", user.getLastName());
        assertEquals("userPassword", user.getPassword());
        assertEquals(1, user.getRoles().size());
        assertEquals("USER", user.getRoles().stream().findFirst().get().getName().name());
    }

    @Test
    void testGetUserByIdNotExisting() {

        assertThrows(ObjectNotFoundException.class,
                () -> this.serviceToTest.getUser(100L));
        try {
            this.serviceToTest.getUser(100L);
        } catch (ObjectNotFoundException exception) {
            assertEquals("user with id 100 not found",
                    exception.getMessage());
        }
    }

    @Test
    void testSaveUser() {
        User user = this.serviceToTest.getUser("userEmail");
        user.setFirstName("newFirstName");
        user.setLastName("newLastName");
        user.setPassword(passwordEncoder.encode("newPassword"));
        this.serviceToTest.saveUser(user);

        User userFromDb = this.serviceToTest.getUser("userEmail");
        assertEquals("newFirstName", userFromDb.getFirstName());
        assertEquals("newLastName", userFromDb.getLastName());
        assertTrue(passwordEncoder.matches("newPassword", userFromDb.getPassword()));
    }

    @Test
    void testFindAllUsers() {
        List<User> users = this.serviceToTest.findAllUsers();
        assertEquals(2, users.size());
        assertEquals("userEmail", users.get(0).getEmail());
        assertEquals("adminEmail", users.get(1).getEmail());
    }

    @Test
    void testDeleteUser() {
        this.serviceToTest.deleteUser(7L);
        List<User> users = this.serviceToTest.findAllUsers();
        assertEquals(2, users.size());
        assertEquals("userEmail", users.get(0).getEmail());
        assertEquals("adminEmail", users.get(1).getEmail());
    }




}