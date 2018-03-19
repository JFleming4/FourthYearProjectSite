package app.models.repository;

import app.models.FileAttachment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileAttachmentRepository extends CrudRepository<FileAttachment, Long> {}
