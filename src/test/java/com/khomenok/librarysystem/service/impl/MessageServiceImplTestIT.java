package com.khomenok.librarysystem.service.impl;

import com.khomenok.librarysystem.errors.ObjectNotFoundException;
import com.khomenok.librarysystem.model.dto.MessageDTO;
import com.khomenok.librarysystem.model.dto.PostMessageDTO;
import com.khomenok.librarysystem.model.entity.Message;
import com.khomenok.librarysystem.model.entity.Role;
import com.khomenok.librarysystem.model.entity.User;
import com.khomenok.librarysystem.model.enums.RoleName;
import com.khomenok.librarysystem.repository.MessageRepository;
import com.khomenok.librarysystem.repository.UserRepository;
import com.khomenok.librarysystem.service.MessageService;
import com.khomenok.librarysystem.service.RoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MessageServiceImplTestIT {

    @Autowired
    private MessageService serviceToTest;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    private User user;

    private User admin;

    private Message openMessage1;

    private Message openMessage2;

    private Message openMessage3;

    private Message closedMessage;

    @BeforeEach
    void setUp() {
        this.messageRepository.deleteAll();
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

        openMessage1 = new Message("title1","question1");
        openMessage1.setUser(user);
        openMessage2 = new Message("title2","question2");
        openMessage2.setUser(user);
        openMessage3 = new Message("title3","question3");
        openMessage3.setUser(admin);
        closedMessage = new Message("title4","question4");
        closedMessage.setUser(user);
        closedMessage.setClosed(true);
        closedMessage.setAdmin(admin);
    }

    @AfterEach
    void tearDown() {
        this.messageRepository.deleteAll();
        this.userRepository.deleteAll();
    }

    @Test
    void testGetAllOpenMessages() {
        this.messageRepository.save(openMessage1);
        this.messageRepository.save(openMessage2);
        this.messageRepository.save(closedMessage);

        List<MessageDTO> openMessages = this.serviceToTest.getOpenMessages();

        assertEquals(2,openMessages.size());
        assertEquals("title1",openMessages.get(0).getTitle());
        assertEquals("question1",openMessages.get(0).getQuestion());
        assertEquals("title2",openMessages.get(1).getTitle());
        assertEquals("question2",openMessages.get(1).getQuestion());
    }

    @Test
    void testRegisterMessage() {
        this.serviceToTest.registerMessage(new PostMessageDTO("title","question"),user.getEmail());

        List<Message> messages = this.messageRepository.findAll();

        assertEquals(1,messages.size());
        assertEquals("title",messages.get(0).getTitle());
        assertEquals("question",messages.get(0).getQuestion());
        assertEquals(user,messages.get(0).getUser());
    }

    @Test
    void testGetUsersMessages() {
        this.messageRepository.save(openMessage1);
        this.messageRepository.save(openMessage2);
        this.messageRepository.save(openMessage3);

        List<MessageDTO> messages = this.serviceToTest.getUsersMessages(user.getEmail());

        assertEquals(2,messages.size());
        assertEquals("title1",messages.get(0).getTitle());
        assertEquals("question1",messages.get(0).getQuestion());
        assertEquals("title2",messages.get(1).getTitle());
        assertEquals("question2",messages.get(1).getQuestion());

    }

    @Test
    void testGetMessage() {
        this.messageRepository.save(openMessage1);
        this.messageRepository.save(openMessage2);
        this.messageRepository.save(openMessage3);

        Message message = this.serviceToTest.getMessage(openMessage2.getId());

        assertEquals(openMessage2.getId(),message.getId());
        assertEquals("title2",message.getTitle());
        assertEquals("question2",message.getQuestion());
        assertEquals(user,message.getUser());
    }

    @Test
    void testGetMessageWithNotExistingId() {
        this.messageRepository.save(openMessage1);
        this.messageRepository.save(openMessage2);
        this.messageRepository.save(openMessage3);

        assertThrows(ObjectNotFoundException.class, () -> {
            this.serviceToTest.getMessage(100L);
        });
        try {
            this.serviceToTest.getMessage(100L);
        } catch (ObjectNotFoundException exception) {
            assertEquals("message not found", exception.getMessage());
        }
    }

    @Test
    void testAnswerMessage() {
        this.messageRepository.save(openMessage1);
        this.messageRepository.save(openMessage2);
        this.messageRepository.save(openMessage3);

        this.serviceToTest.answerMessage(openMessage2.getId(),"response",admin);

        Message message = this.messageRepository.findById(openMessage2.getId()).get();

        assertEquals("response",message.getResponse());
        assertEquals(admin,message.getAdmin());
        assertTrue(message.isClosed());
    }

    @Test
    void testDeleteMessage() {
        this.messageRepository.save(openMessage1);
        this.messageRepository.save(openMessage2);
        this.messageRepository.save(openMessage3);

        this.serviceToTest.deleteMessage(openMessage2.getId());

        List<Message> messages = this.messageRepository.findAll();

        assertEquals(2,messages.size());
        assertEquals(openMessage1.getId(),messages.get(0).getId());
        assertEquals(openMessage3.getId(),messages.get(1).getId());
    }

    @Test
    void testDeleteMessageWithNotExistingId() {
        this.messageRepository.save(openMessage1);
        this.messageRepository.save(openMessage2);
        this.messageRepository.save(openMessage3);

        assertThrows(ObjectNotFoundException.class, () -> {
            this.serviceToTest.deleteMessage(100L);
        });
        try {
            this.serviceToTest.deleteMessage(100L);
        } catch (ObjectNotFoundException exception) {
            assertEquals("message not found", exception.getMessage());
        }
    }

    @Test
    @Transactional
    void testDeleteUserMessages() {
        this.messageRepository.save(openMessage1);
        this.messageRepository.save(openMessage2);
        this.messageRepository.save(openMessage3);
        this.messageRepository.save(closedMessage);

        long count = this.messageRepository.count();

        assertEquals(4,count);

        this.serviceToTest.deleteUserMessages(user.getId());

        List<Message> messages = this.messageRepository.findAll();

        assertEquals(1,messages.size());
        assertEquals(openMessage3.getId(),messages.get(0).getId());
    }



}