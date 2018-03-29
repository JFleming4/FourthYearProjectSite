package app.models.repository;

import app.models.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project findFirstByOrderById();

    Project findById(@Param("id") Long id);
}
