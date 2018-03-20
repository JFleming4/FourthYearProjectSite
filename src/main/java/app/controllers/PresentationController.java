package app.controllers;

import app.models.Day;
import app.models.Project;
import app.models.TimeSlot;
import app.models.repository.ProfessorRepository;
import app.models.repository.ProjectRepository;
import app.models.repository.StudentRepository;
import app.models.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PresentationController {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @GetMapping("/project/{id}/presentation")
    public String presentation(@PathVariable Long id,
                               Model model) {
        Project project = projectRepository.findOne(id);

        model.addAttribute("timeSlots", project.getTimeSlots());
        return "presentation";
    }

    @PostMapping("/project/{id}/presentation/new-time")
    public String newTimeSlot(@PathVariable Long id,
                              @RequestParam("day") String day,
                              @RequestParam("hour") String hour,
                              @RequestParam("minute") String min,
                              Model model) {
        Project project = projectRepository.findOne(id);
        try {
            Day dayParam = Day.valueOf(day);
            int hourParam = new Integer(hour);
            if(hourParam > 18 || hourParam < 8) throw new Exception();
            int minParam = new Integer(min);
            if(minParam != 0 && minParam != 30) throw new Exception();
            TimeSlot ts = new TimeSlot(dayParam, hourParam, minParam);
            project.addTimeSlot(ts);
            timeSlotRepository.save(ts);
            projectRepository.save(project);
        } catch(Exception e) {

        }
        model.addAttribute("timeSlots", project.getTimeSlots());
        return "presentation";
    }
}
