package app.models.repository;

import app.models.AuthenticatedUser;
import org.springframework.data.repository.CrudRepository;

public interface AuthenticatedUserRepository extends CrudRepository<AuthenticatedUser, Long> {
    AuthenticatedUser findByUsername(String username);
    AuthenticatedUser findFirstByOrderById();
}
