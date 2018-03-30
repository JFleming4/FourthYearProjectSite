package app.controllers;

import app.models.AuthenticatedUser;
import app.models.Project;
import app.models.Student;
import app.models.repository.AuthenticatedUserRepository;
import app.models.repository.ProjectRepository;
import app.models.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @Autowired
    private AuthenticatedUserRepository authenticatedUserService;

    @GetMapping("/student")
    public String student(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());
        Student student = authenticatedUser.getStudent();

        model.addAttribute("student", student);
        model.addAttribute("project", student.getProject());

        return "student";
    }
}
