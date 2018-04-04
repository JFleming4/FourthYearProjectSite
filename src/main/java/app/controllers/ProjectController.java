package app.controllers;

import app.models.Project;
import app.models.Student;
import app.models.TimeSlot;
import app.models.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/project/{id}")
    public String project(Model model, @PathVariable Long id)
    {
        Project project = projectRepository.findById(id);
        List<Student> students = project.getStudents();
        List<String> restrictions = project.getRestrictions();
        List<TimeSlot> times = project.getTimeSlots();
        TimeSlot t = null;
        for(TimeSlot time: times){
            if(time.getSelected()) t = time;
        }
        model.addAttribute("selectedTime", t);
        model.addAttribute("project", project);
        model.addAttribute("students", students);
        model.addAttribute("restrictions", restrictions);

        return "project";
    }
}
