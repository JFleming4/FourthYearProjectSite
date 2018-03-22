package app.controllers;

import app.models.Professor;
import app.models.Project;
import app.models.ProjectCoordinator;
import app.models.Student;
import app.models.repository.ProjectRepository;
import app.models.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Controller
public class HomeController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/student/{id}")
    public String student(@PathVariable("id") Long id, Model model) {
        Student student = studentRepository.findOne(id);
        Project project = student.getProject();
        model.addAttribute("student", student);
        model.addAttribute("project", project);

        return "student";
    }
}
