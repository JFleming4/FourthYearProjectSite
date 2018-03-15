package app.controllers;

import app.models.Project;
import app.models.Student;
import app.models.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/student")
    public String student(Model model) {
        Project project = projectRepository.findFirstByOrderById();
        Student student = project.getStudents().get(0);
        model.addAttribute("student", student);
        model.addAttribute("project", project);

        return "student";
    }
}
