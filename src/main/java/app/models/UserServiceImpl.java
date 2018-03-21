package app.models;

import app.models.repository.AuthenticatedUserRepository;
import app.models.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements AuthenticatedUserService{
    @Autowired
    private AuthenticatedUserRepository authenticatedUserRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(AuthenticatedUser authenticatedUser) {
        authenticatedUser.setPassword(bCryptPasswordEncoder.encode(authenticatedUser.getPassword()));
        authenticatedUser.setRoles((List<Role>) roleRepository.findAll());
        authenticatedUserRepository.save(authenticatedUser);
    }

    @Override
    public AuthenticatedUser findByUsername(String username) {
        return authenticatedUserRepository.findByUsername(username);
    }
}
