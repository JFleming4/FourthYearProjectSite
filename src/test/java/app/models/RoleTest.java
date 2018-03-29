package app.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class RoleTest {

    private Role role;
    private AuthenticatedUser authenticatedUser;

    private final String NAME = "ROLE_STUDENT";

    @Before
    public void setup()
    {
        role = new Role();
        authenticatedUser = new AuthenticatedUser();
    }

    @Test
    public void testGetSetName()
    {
        role.setName(NAME);
        assertEquals(NAME, role.getName());
    }

    @Test
    public void testGetSetAuthenticationUsers()
    {
        Set<AuthenticatedUser> users = new HashSet<>();
        users.add(authenticatedUser);

        role.setAuthenticatedUsers(users);
        assertEquals(users, role.getAuthenticatedUsers());
    }
}
