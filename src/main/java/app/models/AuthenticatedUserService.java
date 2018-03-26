package app.models;

public interface AuthenticatedUserService {
    void save(AuthenticatedUser authenticatedUser);

    AuthenticatedUser findByUsername(String username);
}