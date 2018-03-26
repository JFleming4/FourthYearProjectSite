package app.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {
    private Long id;
    private String name;
    private Set<AuthenticatedUser> authenticatedUsers;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "roles")
    public Set<AuthenticatedUser> getAuthenticatedUsers() {
        return authenticatedUsers;
    }

    public void setAuthenticatedUsers(Set<AuthenticatedUser> authenticatedUsers) {
        this.authenticatedUsers = authenticatedUsers;
    }
}
