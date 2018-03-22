package app.controllers;

import app.Application;
import app.TestConfig;
import app.models.Project;
import app.models.repository.ProjectRepository;
import app.models.repository.TimeSlotRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = { TestConfig.class })
@AutoConfigureMockMvc
public class PresentationControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TimeSlotRepository timeSlotRepository;

    private Project project;

    @Before
    public void setup() {
        project = new Project();
        projectRepository.save(project);
    }

    @Test
    public void testPresentationUrl() {
        try {
            mockMvc.perform(get("/project/"+project.getId()+"/presentation"))
                    .andExpect(status().isOk());
        } catch(Exception e) {

        }
    }

    public void testNewTimeSlotUrl() {
        try {
            mockMvc.perform(post("/presentation/new-time"))
                    .andExpect(status().isOk());
        } catch (Exception e) {

        }
    }

    public void testGetTimeSlots() {
        try{
            mockMvc.perform(get("/presentation/get-times"))
                    .andExpect(status().isOk());
        }catch(Exception e) {

        }
    }

}
