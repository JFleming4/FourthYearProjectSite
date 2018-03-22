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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        model.addAttribute("projectId", id);
        return "presentation";
    }
    @GetMapping("/presentation/get-times")
    @ResponseBody
    public List<TimeSlot> getTimeSlots(@RequestParam("id") Long id) {
        Project project = projectRepository.findOne(id);
        List<TimeSlot> times =  project.getTimeSlots();
        // this response is only to render the timeslot and since there is a reference
        // to Project in TimeSlot and a reference to TimeSlot in Project it creates an
        // infinitely long response that the browser won't like
        for(TimeSlot time: times) {
            time.setProject(null);
        }
        return times;
    }
    @PostMapping("/presentation/new-time")
    public String newTimeSlot(@RequestBody String req) {
        String [] params = req.split("&");
        String id = params[0].split("=")[1];
        String day = params[1].split("=")[1];
        String hour = params[2].split("=")[1];
        String minute = params[3].split("=")[1];
        Project project = projectRepository.findOne(new Long(id));
        try {
            Day dayParam = Day.valueOf(day.toUpperCase());
            int hourParam = new Integer(hour);
            int minParam = new Integer(minute);
            TimeSlot ts = new TimeSlot(dayParam, hourParam, minParam);
            boolean added = project.addTimeSlot(ts);
            if(added) {
                timeSlotRepository.save(ts);
                projectRepository.save(project);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "presentation";
    }
}
