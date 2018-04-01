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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = { TestConfig.class })
@Transactional
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private Student student;
    private Project project;

    private final String FIRST = "FirstName";
    private final String LAST = "LastName";
    private final String EMAIL = "first@last.com";
    private final String STUD_NO = "123456789";
    private final String PROG = "SYSC";

    private final String DESCRIPTION = "Project Description";
    private final int MAX_CAPACITY = 3;

    @Before
    public void setup() {
        student = new Student(FIRST, LAST, EMAIL, STUD_NO, PROG);
        project = new Project(null, DESCRIPTION, new ArrayList<>(), MAX_CAPACITY);
    }

    @Test
    public void testLoadStudent() {
        studentRepository.save(student);

        Student actualStudent = studentRepository.findOne(student.getId());

        assertEquals(student, actualStudent);
    }

    @Test
    public void testProjectAssociation() {
        student.joinProject(project);
        studentRepository.save(student);

        Student actualStudent = studentRepository.findOne(student.getId());

        assertEquals(project, actualStudent.getProject());
    }
}
