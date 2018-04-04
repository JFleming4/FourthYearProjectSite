package app.controllers;

import app.Application;
import app.TestConfig;
import app.models.*;
import app.models.repository.AuthenticatedUserRepository;
import app.models.repository.ProfessorRepository;
import app.models.repository.ProjectRepository;
import org.hamcrest.Matchers;
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

import java.util.ArrayList;

import static junit.framework.TestCase.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = { TestConfig.class })
@AutoConfigureMockMvc
public class ProfessorControllerTest {

    private static final String USERNAME = "username";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AuthenticatedUserRepository authenticatedUserRepository;

    private Professor professor;
    private Project project;
    private Student student;

    @Before
    public void setUp()
    {
        student = new Student("Mike", "Hawk", "mikehawk@cmail.carleton.ca", "42", "Software");
        ProjectCoordinator coordinator = new ProjectCoordinator("Sir", "Coordinate", "coordinator@sce.carleton.ca");
        professor = new Professor("Babak", "Esfandiari", "babak@sce.carleton.ca", "1", coordinator);
        project = new Project(professor, "GraphQL Query Planner", new ArrayList<String>(), 4);

        project.addStudent(student);

    }

    @Test
    @WithMockUser(username = USERNAME, roles={"PROFESSOR"})
    public void getProfessorTemplate()
    {
//        authenticateProfessor();
//        try {
//            mockMvc.perform(get("/professor/"))
//                    .andExpect(status().isOk())
//                    .andExpect(content().string(Matchers.containsString("<title>Fourth Year Project - Professor</title>")));
//        } catch (Exception e) {
//            fail(e.getMessage());
//        }
    }

    private void authenticateProfessor()
    {
        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        authenticatedUser.setUsername(USERNAME);
        authenticatedUserRepository.save(authenticatedUser);

        professor.setAuthenticatedUser(authenticatedUser);
        projectRepository.save(project);
    }

}
