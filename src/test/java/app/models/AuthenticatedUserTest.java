package app.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class AuthenticatedUserTest {

    private AuthenticatedUser authenticatedUser;
    private Role role;

    private final String USERNAME = "test_user";
    private final String PASSWORD = "password";

    @Before
    public void setup()
    {
        authenticatedUser = new AuthenticatedUser();
        role = new Role();
        role.setName("ROLE_STUDENT");
    }

    @Test
    public void testGetSetUsername()
    {
        authenticatedUser.setUsername(USERNAME);
        assertEquals(USERNAME, authenticatedUser.getUsername());
    }

    @Test
    public void testGetSetPassword()
    {
        authenticatedUser.setPassword(PASSWORD);
        assertEquals(PASSWORD, authenticatedUser.getPassword());
    }

    @Test
    public void testGetSetPasswordConfirm()
    {
        authenticatedUser.setPasswordConfirm(PASSWORD);
        assertEquals(PASSWORD, authenticatedUser.getPasswordConfirm());
    }

    @Test
    public void testGetSetRoles()
    {
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        authenticatedUser.setRoles(roles);
        assertEquals(roles, authenticatedUser.getRoles());
    }
}
