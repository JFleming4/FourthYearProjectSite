package app.models.repository;

import app.models.FileAttachment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileAttachmentRepository extends CrudRepository<FileAttachment, Long> {
    @Modifying
    @Query("DELETE FROM FileAttachment f WHERE f.id = ?1")
    void delete(Long entityId);
}
