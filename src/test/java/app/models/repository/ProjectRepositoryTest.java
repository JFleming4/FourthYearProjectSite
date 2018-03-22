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
import java.util.List;

import app.models.FileAttachment.FileAttachmentType;

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
    private FileAttachment file;
    private List<String> restrictions;

    private final String FIRST = "FirstName";
    private final String LAST = "LastName";
    private final String EMAIL = "first@last.com";

    private final String PROG = "SYSC";
    private final String REST_PROG = "ARTS";
    private final String AAALRIGHT = "AAAAAAAAAAAAAAAAAlright";

    private final String STUD_NO1 = "1";

    private final String DESCRIPTION = "Project Description";
    private final String NEW_DESC = "New Description";

    private final int MAX_CAPACITY = 2;

    private final String ASSET_URL = "https://s3.amazom.com/files/s/my_file.txt";
    private final FileAttachmentType PROJECT_ASSET_TYPE = FileAttachmentType.FINAL_REPORT;


    @Before
    public void setup() {
        restrictions = new ArrayList<>();
        restrictions.add(REST_PROG);

        projectCoordinator = new ProjectCoordinator(FIRST, LAST, EMAIL);
        professor = new Professor(FIRST, LAST, EMAIL, "1", projectCoordinator);
        student = new Student(FIRST, LAST, EMAIL, STUD_NO1, PROG);

        file = new FileAttachment(ASSET_URL, PROJECT_ASSET_TYPE, project);

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

    @Test
    public void testFileAttachmentAssociation() {
        project.addFile(file);
        projectRepository.save(project);

        Project actualProject = projectRepository.findOne(project.getId());

        assertTrue(actualProject.getFiles().contains(file));
    }
}