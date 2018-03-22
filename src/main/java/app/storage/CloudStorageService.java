package app.storage;

import app.Application;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

@Service
@Profile(Application.PRODUCTION)
public class CloudStorageService implements StorageService {
    private final static String AWS_ACCESS_KEY = System.getenv("SYSC_4806_AWS_ACCESS_KEY");
    private final static String AWS_SECRET_KEY = System.getenv("SYSC_4806_AWS_SECRET_KEY");
    private final static String S3_BUCKET_NAME = System.getenv("SYSC_4806_S3_BUCKET_NAME");

    private AmazonS3 s3client;

    public CloudStorageService(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    public CloudStorageService() {
        AWSCredentials credentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);

        this.s3client = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_EAST_1)
            .build();
    }

    @Override
    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                    "Cannot store file with relative path outside current directory " + filename
                );
            }
            s3client.putObject(S3_BUCKET_NAME, filename, String.valueOf(file.getInputStream()));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return new ArrayList<Path>().stream();
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public void delete(String filename) {
        s3client.deleteObject(S3_BUCKET_NAME, filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        URL key = s3client.getUrl(S3_BUCKET_NAME, filename);
        Resource resource = new UrlResource(key);
        if (resource.exists() || resource.isReadable())
            return resource;
        throw new StorageFileNotFoundException("Could not read file: " + filename);
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void init() {

    }
}
