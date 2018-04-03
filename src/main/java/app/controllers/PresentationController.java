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
        User user;
        boolean isSupervisor = true;
        if(!authenticatedUser.getType().equals("Student")) {
            user = authenticatedUser.getProfessor();
            Project p = projectRepository.findById(id);
            Professor prof = (Professor) user;
            if (prof.getProjects().contains(p)) {
                isSupervisor = true;
            }
        }
        model.addAttribute("project", projectRepository.findById(id));
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
                                  @RequestParam("timeSlot") String id,
                                  @AuthenticationPrincipal UserDetails currentUser) {

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        if(id.split(",").length > 1) {
            System.out.println("skipping");
            return "redirect:/project/" + pid + "/presentation";
        }
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());
        User user;
        Project proj = projectRepository.findOne(pid);
        if(authenticatedUser.getType().equals("Student")) {
            System.out.println("student");
            user = authenticatedUser.getStudent();
            Student stud = (Student) user;
            if(stud.getProject().equals(proj)) {
                System.out.println("about to update");
                updateTimes(id, proj);
            }
        } else {
            System.out.println("professor");
            user = authenticatedUser.getProfessor();
            Professor prof = (Professor) user;
            if(prof.getProjects().contains(proj) || prof.getSecondReaderProjects().contains(proj)) {
                System.out.println("about to update");
                updateTimes(id, proj);
            }
        }
        return "redirect:/project/" + pid + "/presentation";
    }
    @PostMapping("/project/{id}/presentation/new-time")
    public String newTimeSlot(@PathVariable Long id,
                              @RequestParam("day") String day,
                              @RequestParam("hour") String hour,
                              @RequestParam("minute") String minute,
                                @AuthenticationPrincipal UserDetails currentUser) {
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());
        User user;
        Project project = projectRepository.findById(id);
        if(!authenticatedUser.getType().equals("Student")) {
            user = authenticatedUser.getProfessor();
            Professor prof = (Professor) user;
            if(!prof.getProjects().contains(project)) {
                return "redirect:/project/" + id + "/presentation";
            }
        } else {
            return "redirect:/project/" + id + "/presentation";
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
        System.out.println(id);
        for(TimeSlot time: proj.getTimeSlots()) {
            TimeSlot t = timeSlotRepository.findOne(time.getId());
            System.out.println();
            System.out.println(id);
            System.out.println(t.getId());
            System.out.println(t.getId().equals(new Long(id)));
            System.out.println();
            t.setSelected(t.getId().equals(new Long(id)));
            timeSlotRepository.save(t);
        }
    }
}
