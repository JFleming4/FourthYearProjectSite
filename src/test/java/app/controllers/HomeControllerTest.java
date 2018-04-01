package app.controllers;

import app.Application;
import app.TestConfig;
import app.models.*;
import app.models.repository.ProjectRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.hamcrest.Matchers;

import java.util.ArrayList;

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

    private Project project;
    private Student student;

    @Before
    public void setUp() {
        student = new Student("Justin", "Krol", "justinkrol@cmail.carleton.ca", "1", "Software Engineering");
        ProjectCoordinator coordinator = new ProjectCoordinator("Sir", "Coordinate", "coordinator@sce.carleton.ca");
        Professor prof = new Professor("Babak", "Esfandiari", "babak@sce.carleton.ca", "1", coordinator);
        project = new Project(prof, "GraphQL Query Planner", new ArrayList<String>(), 4);

        project.addStudent(student);

    }

    @Test
    @WithMockUser(username = "username", roles={"STUDENT"})
    public void getStudentTemplate() {
        projectRepository.save(project);
        try {
            mockMvc.perform(get("/student/"+student.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().string(Matchers.containsString("<title>Fourth Year Project - Student</title>")));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
