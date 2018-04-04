package app;

import app.Application;
import app.TestConfig;
import app.models.*;
import app.models.repository.ProfessorRepository;
import app.models.repository.ProjectRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.hamcrest.Matchers;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = { TestConfig.class })
@AutoConfigureMockMvc
public class ProfessorControllerTest {
    ProjectCoordinator projectCoordinator;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        projectCoordinator = new ProjectCoordinator("Sir", "Coord", "coord@carleton.ca");

    }

    @Test
    @WithMockUser(username = "username", roles={"COORDINATOR"})
    public void getNewProfessorPage() {
        try {
            mockMvc.perform(get("/professors/new"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(Matchers.containsString("<title>Fourth Year Project - New Professor</title>")));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @WithMockUser(username = "username", roles={"COORDINATOR"})
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void postCreateProfessorSuccess() {
        try {
            mockMvc.perform(
                        post("/professors")
                            .flashAttr("professorForm", new Professor("Justin", "Krol", "justin@carleton.ca", "3", projectCoordinator))
                    ).andExpect(status().is3xxRedirection());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @WithMockUser(username = "username", roles={"COORDINATOR"})
    public void postCreateProfessorFailsWithDuplicateProfNumber() {
        try {
            mockMvc.perform(
                    post("/professors")
                            .flashAttr("professorForm", new Professor("Justin", "Krol", "justin@carleton.ca", "1", projectCoordinator))
            ).andExpect(status().isBadRequest());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
