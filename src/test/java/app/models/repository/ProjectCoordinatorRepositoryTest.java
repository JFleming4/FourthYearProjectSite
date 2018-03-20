package app.models.repository;

import app.Application;
import app.TestConfig;
import app.models.ProjectCoordinator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = { TestConfig.class })
@Transactional
public class ProjectCoordinatorRepositoryTest {
    @Autowired
    private ProjectCoordinatorRepository projectCoordinatorRepository;

    private ProjectCoordinator projectCoordinator;

    private final String FIRST = "FirstName";
    private final String LAST = "LastName";
    private final String EMAIL = "first@last.com";


    @Before
    public void setup() {
        projectCoordinator = new ProjectCoordinator(FIRST, LAST, EMAIL);
    }

    @Test
    public void testEquals() {
        projectCoordinatorRepository.save(projectCoordinator);

        ProjectCoordinator actualProjectCoordinator = projectCoordinatorRepository.findOne(projectCoordinator.getId());

        assertEquals(projectCoordinator, actualProjectCoordinator);
    }
}