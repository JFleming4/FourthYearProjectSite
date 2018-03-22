package app.controllers;

import app.Application;
import app.TestConfig;
import app.models.Project;
import app.models.Student;
import app.models.repository.ProjectRepository;
import app.models.repository.StudentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.hamcrest.Matchers;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = { TestConfig.class })
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private StudentRepository studentRepository;

    private Project project;
    private Student student;

    @Before
    public void setUp() {
        project = new Project();
        student = new Student();
        projectRepository.save(project);
        studentRepository.save(student);
    }

    @Test
    public void getStudentTemplate() {
        try {
            mockMvc.perform(get("/student"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(Matchers.containsString("<title>Fourth Year Project - Student</title>")));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
