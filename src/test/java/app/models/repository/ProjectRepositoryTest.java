package app.models.repository;

import app.Application;
import app.TestConfig;
import app.models.Professor;
import app.models.Project;
import app.models.ProjectCoordinator;
import app.models.Student;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = { TestConfig.class })
@Transactional
public class ProjectRepositoryTest {
    @Autowired
    private ProjectRepository projectRepository;

    private Project project;
    private ProjectCoordinator projectCoordinator;
    private Professor professor;
    private Student student;
    private List<String> restrictions;

    private final String FIRST = "FirstName";
    private final String LAST = "LastName";
    private final String EMAIL = "first@last.com";

    private final String PROG = "SYSC";
    private final String REST_PROG = "ARTS";
    private final String AAALRIGHT = "AAAAAAAAAAAAAAAAAlright";

    private final int STUD_NO1 = 1;

    private final String DESCRIPTION = "Project Description";
    private final String NEW_DESC = "New Description";

    private final int MAX_CAPACITY = 2;


    @Before
    public void setup() {
        restrictions = new ArrayList<>();
        restrictions.add(REST_PROG);

        projectCoordinator = new ProjectCoordinator(FIRST, LAST, EMAIL);
        professor = new Professor(FIRST, LAST, EMAIL, projectCoordinator);
        student = new Student(FIRST, LAST, EMAIL, STUD_NO1, PROG);

        project = new Project(professor, DESCRIPTION, restrictions, MAX_CAPACITY);
    }

    @Test
    public void testProject() {
        projectRepository.save(project);

        Project actualProject = projectRepository.findOne(project.getId());

        assertEquals(project, actualProject);
    }


    @Test
    public void testProjectProfessorAssociation() {
        projectRepository.save(project);

        Project actualProject = projectRepository.findOne(project.getId());

        assertEquals(professor, actualProject.getProjectProf());
    }

    @Test
    public void testSecondReaderAssociation() {
        project.setSecondReader(professor);
        projectRepository.save(project);

        Project actualProject = projectRepository.findOne(project.getId());

        assertEquals(professor, actualProject.getSecondReader());
    }

    @Test
    public void testStudentAssociation() {
        List<Student> students = new ArrayList<>();
        students.add(student);
        project.setStudents(students);
        projectRepository.save(project);

        Project actualProject = projectRepository.findOne(project.getId());

        assertEquals(students, actualProject.getStudents());
    }
}