package app.models.repository;

import app.Application;
import app.TestConfig;
import app.models.AuthenticatedUser;
import app.models.FileAttachment;
import app.models.Project;
import app.models.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import app.models.FileAttachment.FileAttachmentType;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = { TestConfig.class })
@Transactional
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticatedUserRepository authenticatedUserRepository;

    private Role role;
    private AuthenticatedUser authenticatedUser;

    private final String NAME = "ROLE_STUDENT";
    private final String USERNAME = "test";

    @Before
    public void setup() {
        role = new Role();
        role.setName(NAME);

        authenticatedUser = new AuthenticatedUser();
        authenticatedUser.setUsername(USERNAME);
    }

    @Test
    public void testLoadRole() {
        roleRepository.save(role);

        Role actualRole = roleRepository.findOne(role.getId());

        assertEquals(role, actualRole);
    }

    @Test
    public void testAuthenticatedUsersAssociation() {
        authenticatedUserRepository.save(authenticatedUser);
        Set<AuthenticatedUser> users = new HashSet<>();
        users.add(authenticatedUser);

        role.setAuthenticatedUsers(users);
        roleRepository.save(role);

        Role actualRole = roleRepository.findOne(role.getId());

        assertEquals(users, actualRole.getAuthenticatedUsers());
    }
}