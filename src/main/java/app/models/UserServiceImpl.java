package app.models;

import app.models.repository.AuthenticatedUserRepository;
import app.models.repository.ProfessorRepository;
import app.models.repository.RoleRepository;
import app.models.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements AuthenticatedUserService{
    private static final String STUDENT = "Student";
    private static final String PROFESSOR = "Professor";
    private static final String COORDINATOR = "Coordinator";

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private AuthenticatedUserRepository authenticatedUserRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(AuthenticatedUser authenticatedUser) {
        authenticatedUser.setPassword(bCryptPasswordEncoder.encode(authenticatedUser.getPassword()));

        String type = authenticatedUser.getType();
        List<String> assignedRolesNames = new ArrayList<>();
        if(type.equals(STUDENT)) {
            assignedRolesNames.add("ROLE_STUDENT");
            Student student = studentRepository.findByStudentNumber(authenticatedUser.getNumber());
            authenticatedUser.setStudent(student);
            student.setAuthenticatedUser(authenticatedUser);

        }
        else if(type.equals(PROFESSOR)) {
            assignedRolesNames.add("ROLE_PROFESSOR");
            Professor professor = professorRepository.findByProfNumber(authenticatedUser.getNumber());
            authenticatedUser.setProfessor(professor);
            professor.setAuthenticatedUser(authenticatedUser);
        }
        else if(type.equals(COORDINATOR)){
            assignedRolesNames.add("ROLE_PROFESSOR");
            assignedRolesNames.add("ROLE_COORDINATOR");
            Professor professor = professorRepository.findByProfNumber(authenticatedUser.getNumber());
            authenticatedUser.setProfessor(professor);
            professor.setAuthenticatedUser(authenticatedUser);
        }

        authenticatedUser.setRoles((List<Role>) roleRepository.findByNameIn(assignedRolesNames));
        authenticatedUserRepository.save(authenticatedUser);
    }

    @Override
    public AuthenticatedUser findByUsername(String username) {
        return authenticatedUserRepository.findByUsername(username);
    }
}
