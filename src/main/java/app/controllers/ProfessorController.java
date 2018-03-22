package app.controllers;

import app.models.Professor;
import app.models.Project;
import app.models.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping("/professor")
    public String professor(Model model)
    {
        Professor professor = professorRepository.findFirstByOrderById();
        List<Project> projects = professor.getProjects();
        List<Project> secondReader = professor.getSecondReaderProjects();

        model.addAttribute("professor", professor);
        model.addAttribute("projects", projects);
        model.addAttribute("secondReader", secondReader);

        return "professor";
    }
}
