package app.models.repository;

import app.Application;
import app.TestConfig;
import app.models.Professor;
import app.models.Project;
import app.models.ProjectCoordinator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = { TestConfig.class })
@Transactional
public class ProfessorRepositoryTest {
    @Autowired
    private ProfessorRepository professorRepository;

    private Professor professor;
    private ProjectCoordinator projectCoordinator;
    private Project project;

    private List<String> restrictions;

    private final String FIRST = "FirstName";
    private final String LAST = "LastName";
    private final String EMAIL = "first@last.com";

    private final String PROG = "SYSC";

    private final String D1 = "Description 1";

    private final int MAX_CAPACITY = 2;

    @Before
    public void setUp() {
        restrictions = new ArrayList<>();
        restrictions.add(PROG);

        projectCoordinator = new ProjectCoordinator(FIRST, LAST, EMAIL);
        professor = new Professor(FIRST, LAST, EMAIL, projectCoordinator);
        project = new Project(professor, D1, restrictions, MAX_CAPACITY);
    }

    @Test
    public void testProfessor() {
        professorRepository.save(professor);
        Professor actualProfessor = professorRepository.findOne(professor.getId());

        assertEquals(professor, actualProfessor);
    }

    @Test
    public void testProjectAssociation() {
        List<Project> projects = new ArrayList<>();
        projects.add(project);
        professor.setProjects(projects);

        professorRepository.save(professor);
        Professor actualProfessor = professorRepository.findOne(professor.getId());

        assertEquals(projects, actualProfessor.getProjects());
    }

    @Test
    public void testProjectCoordinatorAssociation() {
        professorRepository.save(professor);
        Professor actualProfessor = professorRepository.findOne(professor.getId());

        assertEquals(projectCoordinator, actualProfessor.getProjectCoordinator());
    }

    @Test
    public void testSecondReaderAssociation() {
        List<Project> projects = new ArrayList<>();
        projects.add(project);
        professor.setSecondReaderProjects(projects);

        professorRepository.save(professor);
        Professor actualProfessor = professorRepository.findOne(professor.getId());

        assertEquals(projects, actualProfessor.getSecondReaderProjects());
    }
}