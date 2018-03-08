package app.models;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private User user1, user2;

    private final String FIRST = "FirstName";
    private final String LAST = "LastName";
    private final String EMAIL = "first@last.com";

    private final String NEW_FIRST = "NewFirstName";
    private final String NEW_LAST = "NewLastName";
    private final String NEW_EMAIL = "new_first@last.com";

    @Before
    public void setup()
    {
        user1 = new User(FIRST, LAST, EMAIL);
        user2 = new User(FIRST, LAST, EMAIL);
    }


    @Test
    public void testEquals()
    {
        assert user1.equals(user2);
    }

    @Test
    public void testGetSetFirstName()
    {
        user1.setFirstName(NEW_FIRST);
        assert user1.getFirstName().equals(NEW_FIRST);
    }

    @Test
    public void testGetSetLastName()
    {
        user1.setLastName(NEW_LAST);
        assert user1.getLastName().equals(NEW_LAST);
    }

    @Test
    public void testGetSetEmail()
    {
        user1.setEmail(NEW_EMAIL);
        assert user1.getEmail().equals(NEW_EMAIL);
    }
}
