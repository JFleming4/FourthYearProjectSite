package app.controllers;

import app.Application;
import app.TestConfig;
import app.models.Project;
import app.models.repository.ProjectRepository;
import app.storage.FileSystemStorageService;
import app.storage.StorageFileNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = { TestConfig.class })
@AutoConfigureMockMvc
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileSystemStorageService storageService;

    @Autowired
    private ProjectRepository projectRepository;

    @Mock
    private Resource resource;

    private Project project;
    private String projectUrl;

    private MockMultipartFile uploadedFile;

    private static final String FILENAME = "newFile.txt";
    private static final String ORIGINAL_FILENAME = "file.txt";
    private static final String CONTENT_TYPE = ".txt";
    private static final byte[] CONTENT = new byte[] { 0 };

    @Before
    public void setUp() {
        project = new Project();
        projectRepository.save(project);
        projectUrl = "/project/" + project.getId();

        uploadedFile = new MockMultipartFile(FILENAME, ORIGINAL_FILENAME, CONTENT_TYPE, CONTENT);
    }

    @Test
    public void contexLoads() {
        assertNotNull(mockMvc);
        assertNotNull(storageService);
        assertNotNull(resource);
    }

    @Test
    public void serveFile() {
        try {
            byte[] byteContent = new byte[] { (byte) 0 };
            when(storageService.loadAsResource(anyString())).thenReturn(resource);
            when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(byteContent));

            mockMvc.perform(get("/files/filename.txt"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(new String(byteContent)));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void uploadProposal() {
        try {
            mockMvc.perform(fileUpload(projectUrl + "/upload_proposal")
                .file("file", uploadedFile.getBytes()))
                .andExpect(status().is3xxRedirection());
            verify(storageService).store(any(MultipartFile.class));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void uploadReport() {
        try {
            mockMvc.perform(fileUpload(projectUrl + "/upload_final_report")
                    .file("file", uploadedFile.getBytes()))
                    .andExpect(status().is3xxRedirection());
            verify(storageService).store(any(MultipartFile.class));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void handleStorageFileNotFound() {
        try {
            when(storageService.loadAsResource(anyString())).thenThrow(StorageFileNotFoundException.class);

            mockMvc.perform(get("/files/filename.txt"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}