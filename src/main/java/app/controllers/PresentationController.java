package app.controllers;

import app.models.repository.ProfessorRepository;
import app.models.repository.ProjectRepository;
import app.models.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PresentationController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @GetMapping("/presentation")
    public String presentation(Model model) {
        return "presentation";
    }
}
