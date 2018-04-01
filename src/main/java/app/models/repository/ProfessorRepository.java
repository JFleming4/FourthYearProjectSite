package app.models.repository;

import app.models.Professor;
import app.models.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Long> {
    Professor findFirstByOrderById();
    //Professor findById(@Param("id") Long id);
    Professor findByProfNumber(String number);
    boolean existsByProfNumber(String number);
}
