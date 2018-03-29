package app.models;

import app.models.repository.AuthenticatedUserRepository;
import app.models.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticatedUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AuthenticatedUserRepository authenticatedUserRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthenticatedUser authenticatedUser = authenticatedUserRepository.findByUsername(username);
        if(authenticatedUser == null) {
            throw new UsernameNotFoundException("Could not find user with username: " + username + ".");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for(Role role : authenticatedUser.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(authenticatedUser.getUsername(), authenticatedUser.getPassword(), grantedAuthorities);
    }
}
