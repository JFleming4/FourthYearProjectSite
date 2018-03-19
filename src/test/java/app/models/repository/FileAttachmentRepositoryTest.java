package app.models.repository;

import app.Application;
import app.TestConfig;
import app.models.FileAttachment;
import app.models.Project;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import app.models.FileAttachment.FileAttachmentType;

import javax.transaction.Transactional;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = { TestConfig.class })
@Transactional
@ActiveProfiles(Application.DEVELOPMENT)
public class FileAttachmentRepositoryTest {

    @Autowired
    private FileAttachmentRepository fileAttachmentRepository;

    private FileAttachment file;
    private Project project;

    private final String ASSET_URL = "https://s3.amazom.com/files/s/my_file.txt";
    private final FileAttachmentType PROJECT_ASSET_TYPE = FileAttachmentType.FINAL_REPORT;

    private final String DESCRIPTION = "Project Description";
    private final int MAX_CAPACITY = 2;

    @Before
    public void setup() {
        project = new Project(null, DESCRIPTION, new ArrayList<>(), MAX_CAPACITY);
        file = new FileAttachment(ASSET_URL, PROJECT_ASSET_TYPE, project);
    }

    @Test
    public void testLoadFileAttachment() {
        fileAttachmentRepository.save(file);

        FileAttachment actualFileAttachment = fileAttachmentRepository.findOne(file.getId());

        assertEquals(file, actualFileAttachment);
    }

    @Test
    public void testProjectAssociation() {
        fileAttachmentRepository.save(file);

        FileAttachment actualFileAttachment = fileAttachmentRepository.findOne(file.getId());

        assertEquals(project, actualFileAttachment.getProject());
    }
}