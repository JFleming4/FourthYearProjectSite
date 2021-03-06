package app.controllers;

import app.models.*;
import app.models.repository.*;
import app.models.validators.ProfessorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private ProfessorValidator professorValidator;

    private Professor professor, reader;
    private Project project;
    private Student student;


    @RequestMapping(value = "/facultyMenu", method = RequestMethod.GET)
    public String professor(@AuthenticationPrincipal UserDetails currentUser, Model model/*, @PathVariable Long id*/)
    {
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());
        Professor professor = authenticatedUser.getProfessor();

        model.addAttribute("professor", professor);
        model.addAttribute("projects", professor.getProjects());
        model.addAttribute("secondReader", professor.getSecondReaderProjects());

        if(professor instanceof ProjectCoordinator) {
            List<Professor> professors = (List) professorRepository.findAll();
            List<Student> students = (List) studentRepository.findAll();

            model.addAttribute("professors", professors);
            model.addAttribute("students", students);
        }

        return "professor";
    }

    /**
     * Navigate to the New Project Page
     * @return Redirect to the "new-project" page
     */
    @RequestMapping("/new-project")
    public ModelAndView createNewProject()
    {
        return new ModelAndView("new-project","command", new Project());
    }

    @RequestMapping(value = "/professors/new", method = RequestMethod.GET)
    public String newProfessor(Model model) {
        model.addAttribute("professorForm", new Professor(null, null, null, null, null));
        return "professor/new";
    }

    @RequestMapping(value = "/professors", method = RequestMethod.POST)
    public String createProfessor(@AuthenticationPrincipal UserDetails currentUser,
                                  @ModelAttribute("professorForm") Professor professorForm,
                                  BindingResult bindingResult,
                                  Model model,
                                  RedirectAttributes redirectAttributes,
                                  HttpServletResponse response) {
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());
        ProjectCoordinator coordinator = (ProjectCoordinator) authenticatedUser.getProfessor();
        professorForm.setProjectCoordinator(coordinator);
        professorValidator.validate(professorForm, bindingResult);
        if (bindingResult.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "professor/new";
        }
        professorRepository.save(professorForm);

        redirectAttributes.addFlashAttribute("message","Professor succesfully created");
        return "redirect:/facultyMenu";
    }


    /**
     * Save the Project to the Professor
     * @param currentUser Logged in User
     * @param description Project Description
     * @param restrictions Project Restrictions
     * @param capacity Project Capacity
     * @return Redirect to the "facultyMenu" page
     */
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

        return new ModelAndView("redirect:/facultyMenu");
    }


    /**
     * Navigate to the Add Student Page
     * @param model Model
     * @param projectID Project ID to link Student
     * @return Redirect to the "new-student" page
     */
    @RequestMapping("project/{projectID}/new-student")
    public ModelAndView addStudentById(Model model, @PathVariable("projectID") Long projectID)
    {
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("projectID", projectID);
        return new ModelAndView("new-student","command", new Student());
    }


    /**
     * Save the Student to the Project
     * @param currentUser Logged in user
     * @param id Student's ID
     * @param projectID Project's ID
     * @return Redirect to the "facultyMenu" page
     */
    @RequestMapping(value = "/save-student", method = RequestMethod.POST)
    public ModelAndView addStudent(@AuthenticationPrincipal UserDetails currentUser,
                                   @RequestParam("id") Long id,
                                   @RequestParam("projectID") Long projectID)
    {
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());

        if (authenticatedUser.getType().equals("Professor"))
        {
            Professor professor = authenticatedUser.getProfessor();
            project = projectRepository.findOne(projectID);

            if (!professor.getProjects().contains(project))
            {
                return new ModelAndView("redirect:/facultyMenu");
            }

            student = studentRepository.findOne(id);
            project.addStudent(student);
            professorRepository.save(professor);
        }
        return new ModelAndView("redirect:/facultyMenu");
    }

    /**
     * Navigate to the Add Second Reader page
     * @param model Model
     * @param projectID Project ID to link Second Reader
     * @return Redirect to "add-reader" page
     */
    @RequestMapping("project/{projectID}/add-reader")
    public ModelAndView addReaderById(Model model, @PathVariable("projectID") Long projectID)
    {
        model.addAttribute("readers", professorRepository.findAll());
        model.addAttribute("projectID", projectID);
        return new ModelAndView("add-reader","command", new Professor());
    }

    /**
     * Save Second Reader to the Project
     * @param currentUser Logged in user
     * @param id Second Reader's ID
     * @param projectID Project's ID
     * @return Redirect to the "facultyMenu" page
     */
    @RequestMapping(value = "/save-reader", method = RequestMethod.POST)
    public ModelAndView addReader(@AuthenticationPrincipal UserDetails currentUser,
                                  @RequestParam("id") Long id,
                                  @RequestParam("projectID") Long projectID)
    {
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());

        if (authenticatedUser.getType().equals("Professor"))
        {
            Professor professor = authenticatedUser.getProfessor();
            project = projectRepository.findOne(projectID);

            if (!professor.getProjects().contains(project))
            {
                return new ModelAndView("redirect:/facultyMenu");
            }

            reader = professorRepository.findOne(id);
            project.setSecondReader(reader);
            professorRepository.save(professor);
        }
        return new ModelAndView("redirect:/facultyMenu");
    }


    /**
     * Archive/Unarchive a project from the repository
     * @param projectID The project to be archived/unarchived
     * @return Redirect to the professor page
     */
    @RequestMapping(value = "facultyMenu/archiveProject/{projectID}", method = RequestMethod.GET)
    public ModelAndView archive(@AuthenticationPrincipal UserDetails currentUser, @PathVariable("projectID") Long projectID)
    {
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());
        Professor professor = authenticatedUser.getProfessor();

        project = professor.getProject(projectID);
        project.toggleIsArchived();
        projectRepository.save(project);

        return new ModelAndView("redirect:/facultyMenu");
    }


    /**
     * Delete a project from the repository
     * @param projectID The project to be deleted
     * @return Redirect to the professor page
     */
    @RequestMapping(value = "/facultyMenu/deleteProject/{projectID}", method = RequestMethod.GET)
    public ModelAndView delete(@AuthenticationPrincipal UserDetails currentUser, @PathVariable("projectID") Long projectID)
    {
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());
        Professor professor = authenticatedUser.getProfessor();

        project = professor.getProject(projectID);
        project.setSecondReader(null);
        project.setProjectProf(null);
        for (Student student: project.getStudents()) {
            student.setProject(null);
        }
        project.setStudents(null);

        projectRepository.delete(project);

        return new ModelAndView("redirect:/facultyMenu");
    }
}
