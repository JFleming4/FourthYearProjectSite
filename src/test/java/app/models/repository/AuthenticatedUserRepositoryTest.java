package app.models.repository;

import app.Application;
import app.TestConfig;
import app.models.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = { TestConfig.class })
@Transactional
public class AuthenticatedUserRepositoryTest {

    @Autowired
    private AuthenticatedUserRepository authenticatedUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    private AuthenticatedUser authenticatedUser;

    private final String USERNAME = "FirstName";
    private final String PASSWORD = "LastName";
    private final String[] ROLES = {"ROLE_STUDENT", "ROLE_PROFESSOR", "ROLE_COORDINATOR"};

    @Before
    public void setup() {
        List<String> roles = Arrays.asList(ROLES);
        for(String roleName : roles) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }

        authenticatedUser = new AuthenticatedUser();
        authenticatedUser.setUsername(USERNAME);
        authenticatedUser.setPassword(PASSWORD);
    }

    @Test
    public void testLoadStudent() {
        authenticatedUserRepository.save(authenticatedUser);

        AuthenticatedUser actualAuthenticatedUser = authenticatedUserRepository.findOne(authenticatedUser.getId());
        assertEquals(authenticatedUser, actualAuthenticatedUser);
    }

    @Test
    public void testRolesAssociation() {
        // Get the actual role records
        List<String> roleNames = new ArrayList<String>();
        roleNames.add(ROLES[0]);
        List<Role> roles = roleRepository.findByNameIn(roleNames);
        authenticatedUser.setRoles(roles);

        authenticatedUserRepository.save(authenticatedUser);

        AuthenticatedUser actualAuthenticatedUser = authenticatedUserRepository.findOne(authenticatedUser.getId());
        assertEquals(roles, actualAuthenticatedUser.getRoles());
    }
}
