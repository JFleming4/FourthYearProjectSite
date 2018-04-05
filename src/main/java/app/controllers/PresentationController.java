package app.controllers;

import app.models.*;
import app.models.Day;
import app.models.Project;
import app.models.TimeSlot;
import app.models.repository.ProjectRepository;
import app.models.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PresentationController {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @GetMapping("/project/{id}/presentation")
    public String presentation(@PathVariable Long id,
                               Model model,
                                @AuthenticationPrincipal UserDetails currentUser) {
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());
        boolean isSupervisor = false;
        Project p = projectRepository.findById(id);
        if(!authenticatedUser.getType().equals("Student")) {
            Professor prof = authenticatedUser.getProfessor();
            isSupervisor = prof.getProjects().contains(p);

        }
        model.addAttribute("project", p);
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
    @PostMapping("/project/{pid}/presentation/update")
    public String updateTimeSlots(@PathVariable Long pid,
                                  @RequestParam(value = "timeSlot", required = false) String id,
                                  @AuthenticationPrincipal UserDetails currentUser) {
        Project proj = projectRepository.findOne(pid);
        if(id == null) {
            updateTimes("-1", proj);
            return "redirect:/project/" + pid + "/presentation";
        }
        if(id.split(",").length > 1) {
            return "redirect:/project/" + pid + "/presentation";
        }
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());


        if(authenticatedUser.getType().equals("Student")) {
            Student stud = authenticatedUser.getStudent();
            if(!stud.getProject().equals(proj)) {
                return "redirect:/project/" + stud.getProject().getId() + "/presentation";
            }
        } else {
            Professor prof = authenticatedUser.getProfessor();
            if(!prof.getProjects().contains(proj) && !prof.getSecondReaderProjects().contains(proj)) {
                return "redirect:/facultyMenu";
            }
        }
        updateTimes(id, proj);
        return "redirect:/project/" + pid + "/presentation";
    }
    @PostMapping("/project/{id}/presentation/new-time")
    public String newTimeSlot(@PathVariable Long id,
                              @RequestParam("day") String day,
                              @RequestParam("hour") String hour,
                              @RequestParam("minute") String minute,
                                @AuthenticationPrincipal UserDetails currentUser) {
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());
        Project project = projectRepository.findById(id);
        if(authenticatedUser.getType().equals("Student")) {
            return "redirect:/project/" + authenticatedUser.getStudent().getProject().getId() + "/presentation";

        } else {
            Professor prof = authenticatedUser.getProfessor();
            if(!prof.getProjects().contains(project)) {
                return "redirect:/facultyMenu";
            }
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
        return "redirect:/project/" + id + "/presentation";
    }

    public void updateTimes(String id, Project proj) {
        for(TimeSlot time: proj.getTimeSlots()) {
            time.setSelected(time.getId().equals(new Long(id)));
            timeSlotRepository.save(time);
        }
    }
}
