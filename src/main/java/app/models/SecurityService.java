package app.models;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}