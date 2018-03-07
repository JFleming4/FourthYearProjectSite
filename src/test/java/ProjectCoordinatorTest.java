import org.junit.Before;
import org.junit.Test;

public class ProjectCoordinatorTest {


    private ProjectCoordinator projectCoordinator1;
    private ProjectCoordinator projectCoordinator2;

    private final String FIRST = "FirstName";
    private final String LAST = "LastName";
    private final String EMAIL = "first@last.com";


    @Before
    public void setup()
    {
        projectCoordinator1 = new ProjectCoordinator(FIRST, LAST, EMAIL);
        projectCoordinator2 = new ProjectCoordinator(FIRST, LAST, EMAIL);
    }

    @Test
    public void testEquals()
    {
        assert projectCoordinator1.equals(projectCoordinator2);
    }

}
