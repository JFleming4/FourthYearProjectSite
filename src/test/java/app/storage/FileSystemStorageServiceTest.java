package app.storage;

import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class FileSystemStorageServiceTest {

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();
    private StorageService storageService;
    private File folder;
    private Path rootPath;

    private MultipartFile uploadedFile;

    private static final String FILENAME = "newFile.txt";
    private static final String ORIGINAL_FILENAME = "file.txt";
    private static final String CONTENT_TYPE = ".txt";
    private static final byte[] CONTENT = new byte[] { 0 };

    @Before
    public void setUp() throws Exception {
        folder = tmpFolder.newFolder();
        storageService = new FileSystemStorageService(new StorageProperties(folder.getPath()));
        uploadedFile = new MockMultipartFile(FILENAME, ORIGINAL_FILENAME, CONTENT_TYPE, CONTENT);
        rootPath = Paths.get(folder.getPath());
    }

    @Test
    public void store() {
        storageService.store(uploadedFile);
        assertTrue(uploadedFile().exists());
    }

    @Test
    public void loadAll() {
        String secondFilename = "secondFile.txt";
        store();
        storageService.store(new MockMultipartFile(FILENAME, secondFilename, CONTENT_TYPE, CONTENT));


        Path path1 = rootPath.relativize(uploadedFile().toPath());
        Path path2 = rootPath.relativize(uploadedFile(secondFilename).toPath());
        List<Path> paths = new ArrayList<>();
        paths.add(path1);
        paths.add(path2);

        storageService.loadAll().forEach(path -> {
            assertThat(paths, hasItem(path));
        });

    }

    @Test
    public void load() {
        store();
        assertEquals(uploadedFile().toPath(), storageService.load(ORIGINAL_FILENAME));
    }

    @Test
    public void delete() {
        store();
        storageService.delete(ORIGINAL_FILENAME);

        assertFalse(uploadedFile().exists());
    }

    @Test
    public void loadAsResource() {
        store();
        Resource resource = storageService.loadAsResource(ORIGINAL_FILENAME);

        try {
            assertEquals(uploadedFile(), resource.getFile());
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void deleteAll() {
        storageService.deleteAll();
        assertFalse(folder.exists());
    }

    @Test
    public void init() {
        folder.delete();
        assertFalse(folder.exists());
        storageService.init();
        assertTrue(folder.exists());
    }

    private File uploadedFile() {
        return uploadedFile(ORIGINAL_FILENAME);
    }

    private File uploadedFile(String originalFilename) {
        return rootPath.resolve(originalFilename).toFile();
    }
}