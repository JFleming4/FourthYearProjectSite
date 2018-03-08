package app.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import app.models.FileAttachment.FileAttachmentType;

import static org.junit.Assert.*;

public class FileAttachmentTest {
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
    public void equals() {
        FileAttachment newFile = new FileAttachment();
        assertNotEquals(file, newFile);

        newFile.setAssetUrl(ASSET_URL);
        assertNotEquals(file, newFile);

        newFile.setProjectAssetType(PROJECT_ASSET_TYPE);
        assertNotEquals(file, newFile);

        newFile.setProject(project);
        assertEquals(file, newFile);
    }
}