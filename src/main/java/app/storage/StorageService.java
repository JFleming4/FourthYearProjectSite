package app.storage;

import app.models.Project;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    void init();

    void store(String filename, MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    void delete(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();
}
