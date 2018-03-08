package app.models.repository;

import app.models.ProjectCoordinator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectCoordinatorRepository extends CrudRepository<ProjectCoordinator, Long> {

}
