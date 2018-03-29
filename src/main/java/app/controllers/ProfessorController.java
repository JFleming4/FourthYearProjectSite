package app.controllers;

import app.models.Professor;
import app.models.Project;
import app.models.Student;
import app.models.repository.ProfessorRepository;
import app.models.repository.ProjectRepository;
import app.models.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private StudentRepository studentRepository;

    private Professor professor;
    private Project project;
    private Student student;

    @RequestMapping(value = "/professor", method = RequestMethod.GET)
    public String professor(Model model/*, @PathVariable Long id*/)
    {
        //professor = professorRepository.findById(id);
        professor = professorRepository.findFirstByOrderById();
        List<Project> projects = professor.getProjects();
        List<Project> secondReader = professor.getSecondReaderProjects();

        model.addAttribute("professor", professor);
        model.addAttribute("projects", projects);
        model.addAttribute("secondReader", secondReader);

        return "professor";
    }

    @RequestMapping("/new-project")
    public ModelAndView createNewProject()
    {
        return new ModelAndView("new-project","command", new Project());
    }


    // Have to get the professor's ID
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveProject(@RequestParam("desc") String description, @RequestParam("rest") String restrictions, @RequestParam("cap") int capacity)
    {
        //professor = professorRepository.findById();
        System.out.println("Description: " + description);
        System.out.println("Restrictions: " + restrictions);
        System.out.println("Capacity: " + capacity);
        List<String> restrictionsList = Arrays.asList(restrictions.split(","));

        professor = professorRepository.findFirstByOrderById();
        professor.createProject(description, restrictionsList, capacity);
        professorRepository.save(professor);

        return new ModelAndView("redirect:/professor"/* + professor.getId()*/);
    }

    @RequestMapping("/new-student/")
    public ModelAndView addStudentById()
    {
        return new ModelAndView("new-student","command", new Student());
    }


    // Not currently working. Need to change the confirmation workflow
    @RequestMapping(value = "/save-student", method = RequestMethod.POST)
    public ModelAndView addStudent(@RequestParam("id") int id)
    {
        //professor = professorRepository.findById();
        student = studentRepository.findOne((long) id);
        professor = professorRepository.findFirstByOrderById();

        professorRepository.save(professor);

        return new ModelAndView("redirect:/professor"/* + professor.getId()*/);
    }


    /**
     * Archive/Unarchive a project from the repository
     * @param projectID The project to be archived/unarchived
     * @return Redirect to the professor page
     */
    @RequestMapping(value = "professor/archiveProject/{projectID}", method = RequestMethod.GET)
    public ModelAndView archive(/*@PathVariable("id") String id, */@PathVariable("projectID") Long projectID)
    {
        //professor = professorRepository.findById(Long.parseLong(id));
        professor = professorRepository.findFirstByOrderById();
        project = professor.getProject(projectID);
        project.toggleIsArchived();
        projectRepository.save(project);

        return new ModelAndView("redirect:/professor"/* + id*/);
    }


    /**
     * Delete a project from the repository
     * @param projectID The project to be deleted
     * @return Redirect to the professor page
     */
    @RequestMapping(value = "/professor/deleteProject/{projectID}", method = RequestMethod.GET)
    public ModelAndView delete(/*@PathVariable("id") String id, */@PathVariable("projectID") Long projectID)
    {
        //professor = professorRepository.findById(Long.parseLong(id));
        professor = professorRepository.findFirstByOrderById();

        project = professor.getProject(projectID);
        project.setProjectProf(null);

        projectRepository.delete(project);

        return new ModelAndView("redirect:/professor"/* + id*/);
    }

}
