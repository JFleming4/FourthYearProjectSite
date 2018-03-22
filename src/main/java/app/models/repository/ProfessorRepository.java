package app.models.repository;

import app.models.Professor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Long> {
    Professor findFirstByOrderById();
}
