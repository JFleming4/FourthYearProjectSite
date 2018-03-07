package app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class HomeController {
    private Student student1, student2;
    private ProjectCoordinator coordinator;
    private Professor prof1, prof2;
    private Project project;

    @GetMapping("/student")
    public String student(Model model) {
        // This is some sample data until the db is hooked up
        Student student1 = new Student("Justin", "Krol", "justinkrol@cmail.carleton.ca", 100990999, "Software Engineering");
        Student student2 = new Student("Derek", "Stride", "derekstride@cmail.carleton.ca", 100440444, "Software Engineering");
        ProjectCoordinator coordinator = new ProjectCoordinator("Sir", "Coordinate", "coordinator@sce.carleton.ca");
        Professor prof1 = new Professor("Babak", "Esfandiari", "babak@sce.carleton.ca", coordinator);
        Professor prof2 = new Professor("Samuel", "Ajila", "ajila@sce.carleton.ca", coordinator);
        Project project = new Project(prof1, "GraphQL Query Planner", new ArrayList<String>(), 4);

        project.addStudent(student1);
        project.addStudent(student2);


        model.addAttribute("project", project);
        model.addAttribute("student", student1);
        return "student";
    }
}
