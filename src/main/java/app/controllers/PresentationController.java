package app.controllers;

import app.WebSecurityConfig;
import app.models.*;
import app.models.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    @Autowired
    private AuthenticatedUserService authenticatedUserService;
    @Autowired
    private ProfessorRepository professorRepository;
    @GetMapping("/project/{id}/presentation")
    public String presentation(@PathVariable Long id,
                               Model model,
                               @AuthenticationPrincipal UserDetails currentUser) {
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());
        User user;
        boolean isSupervisor = false;
        if(!authenticatedUser.getType().equals("Student")) {
            user = authenticatedUser.getProfessor();
            Project p = projectRepository.findById(id);
            Professor prof = (Professor) user;
            if(prof.getProjects().contains(p)) {
                isSupervisor = true;
            }
        }
        model.addAttribute("projectId", id);
        model.addAttribute("isSupervisor", isSupervisor);
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
    public String newTimeSlot(@RequestBody String req,
                              @AuthenticationPrincipal UserDetails currentUser) {
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());
        User user;
        String [] params = req.split("&");
        String id = params[0].split("=")[1];
        String day = params[1].split("=")[1];
        String hour = params[2].split("=")[1];
        String minute = params[3].split("=")[1];
        Project project = projectRepository.findOne(new Long(id));
        if(!authenticatedUser.getType().equals("Student")) {
            user = authenticatedUser.getProfessor();
            Professor prof = (Professor) user;
            if(!prof.getProjects().contains(project)) {
                return "presentation";
            }
        } else {
            return "presentation";
        }
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
