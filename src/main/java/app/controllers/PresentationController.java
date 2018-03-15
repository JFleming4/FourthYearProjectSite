package app.controllers;

import app.models.TimeSlot;
import app.models.repository.ProfessorRepository;
import app.models.repository.ProjectRepository;
import app.models.repository.StudentRepository;
import app.models.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PresentationController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @GetMapping("/presentation")
    public String presentation(Model model) {

        Iterable<TimeSlot> it = timeSlotRepository.findAll();
        ArrayList<TimeSlot> list = new ArrayList<TimeSlot>();
        it.forEach(list::add);

        model.addAttribute("timeSlots", list);
        return "presentation";
    }
}
