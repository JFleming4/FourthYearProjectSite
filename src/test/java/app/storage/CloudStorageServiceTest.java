package app.storage;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CloudStorageServiceTest {

    private AmazonS3 mockedS3;

    private StorageService storageService;
    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    private MultipartFile uploadedFile;

    private static final String FILENAME = "newFile.txt";
    private static final String ORIGINAL_FILENAME = "file.txt";
    private static final String CONTENT_TYPE = ".txt";
    private static final byte[] CONTENT = new byte[] { 0 };

    @Before
    public void setUp() {
        mockedS3 = mock(AmazonS3.class);
        storageService = new CloudStorageService(mockedS3);
        uploadedFile = new MockMultipartFile(FILENAME, ORIGINAL_FILENAME, CONTENT_TYPE, CONTENT);
    }

    @Test
    public void store() {
        storageService.store(uploadedFile);

        verify(mockedS3).putObject(anyString(), eq(uploadedFile.getOriginalFilename()), anyString());
    }

    @Test
    public void delete() {
        storageService.delete(ORIGINAL_FILENAME);

        verify(mockedS3).deleteObject(anyString(), eq(ORIGINAL_FILENAME));
    }

    @Test
    public void loadAsResource() {
        try {
            URLConnection mockUrlCon = mock(URLConnection.class);
            File file = tmpFolder.newFile();

            when(mockUrlCon.getInputStream()).thenReturn(new FileInputStream(file));

            URLStreamHandler stubUrlHandler = new URLStreamHandler() {
                @Override
                protected URLConnection openConnection(URL u) {
                    return mockUrlCon;
                }
            };

            URL fileUrl = file.toURI().toURL();
            URL url = new URL(
                fileUrl.getProtocol(),
                fileUrl.getHost(),
                fileUrl.getPort(),
                fileUrl.getPath(),
                stubUrlHandler);


            when(mockedS3.getUrl(anyString(), eq(ORIGINAL_FILENAME))).thenReturn(url);
            Resource resource = storageService.loadAsResource(ORIGINAL_FILENAME);

            assertEquals(file, resource.getFile());
            verify(mockedS3).getUrl(anyString(), eq(ORIGINAL_FILENAME));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Ignore
    @Test
    public void loadAll() {
        // CloudStorageService.loadAll() is a no op
    }

    @Ignore
    @Test
    public void load() {
        // CloudStorageService.load(filename: String) is a no op
    }

    @Ignore
    @Test
    public void deleteAll() {
        // CloudStorageService.deleteAll() is a no op
    }

    @Ignore
    @Test
    public void init() {
        // CloudStorageService.init() is a no op
    }
}