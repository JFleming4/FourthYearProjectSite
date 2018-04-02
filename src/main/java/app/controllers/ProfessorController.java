package app.controllers;

import app.models.AuthenticatedUser;
import app.models.Professor;
import app.models.Project;
import app.models.Student;
import app.models.repository.AuthenticatedUserRepository;
import app.models.repository.ProfessorRepository;
import app.models.repository.ProjectRepository;
import app.models.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private AuthenticatedUserRepository authenticatedUserService;

    private Professor professor;
    private Project project;
    private Student student;

    @RequestMapping(value = "/professor", method = RequestMethod.GET)
    public String professor(@AuthenticationPrincipal UserDetails currentUser, Model model/*, @PathVariable Long id*/)
    {
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());
        Professor professor = authenticatedUser.getProfessor();

        model.addAttribute("professor", professor);
        model.addAttribute("projects", professor.getProjects());
        model.addAttribute("secondReader", professor.getSecondReaderProjects());

        return "professor";
    }

    @RequestMapping("/new-project")
    public ModelAndView createNewProject()
    {
        return new ModelAndView("new-project","command", new Project());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveProject(@AuthenticationPrincipal UserDetails currentUser,
                                    @RequestParam("desc") String description,
                                    @RequestParam("rest") String restrictions,
                                    @RequestParam("cap") int capacity)
    {
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());
        Professor professor = authenticatedUser.getProfessor();

        List<String> restrictionsList = Arrays.asList(restrictions.split(","));

        professor.createProject(description, restrictionsList, capacity);
        professorRepository.save(professor);

        return new ModelAndView("redirect:/professor");
    }

    @RequestMapping("/new-student/")
    public ModelAndView addStudentById()
    {
        return new ModelAndView("new-student","command", new Student());
    }


    // Not currently working. Need to change the confirmation workflow
    @RequestMapping(value = "/save-student", method = RequestMethod.POST)
    public ModelAndView addStudent(@AuthenticationPrincipal UserDetails currentUser, @RequestParam("id") Long id)
    {
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());
        Professor professor = authenticatedUser.getProfessor();
        student = studentRepository.findOne(id);

        professorRepository.save(professor);

        return new ModelAndView("redirect:/professor"/* + professor.getId()*/);
    }


    /**
     * Archive/Unarchive a project from the repository
     * @param projectID The project to be archived/unarchived
     * @return Redirect to the professor page
     */
    @RequestMapping(value = "professor/archiveProject/{projectID}", method = RequestMethod.GET)
    public ModelAndView archive(@AuthenticationPrincipal UserDetails currentUser, @PathVariable("projectID") Long projectID)
    {
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());
        Professor professor = authenticatedUser.getProfessor();

        project = professor.getProject(projectID);
        project.toggleIsArchived();
        projectRepository.save(project);

        return new ModelAndView("redirect:/professor");
    }


    /**
     * Delete a project from the repository
     * @param projectID The project to be deleted
     * @return Redirect to the professor page
     */
    @RequestMapping(value = "/professor/deleteProject/{projectID}", method = RequestMethod.GET)
    public ModelAndView delete(@AuthenticationPrincipal UserDetails currentUser, @PathVariable("projectID") Long projectID)
    {
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());
        Professor professor = authenticatedUser.getProfessor();

        project = professor.getProject(projectID);
        project.setProjectProf(null);

        projectRepository.delete(project);

        return new ModelAndView("redirect:/professor");
    }
}
