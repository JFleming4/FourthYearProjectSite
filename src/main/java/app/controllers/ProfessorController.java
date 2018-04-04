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

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    private Professor professor;
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

        return "professor";
    }

    @RequestMapping(value = "/coordinator", method = RequestMethod.GET)
    public String coordinator(Model model, @AuthenticationPrincipal UserDetails currentUser)
    {
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());
        Professor coordinator = authenticatedUser.getProfessor();
        List<Professor> professors = (List) professorRepository.findAll();

        model.addAttribute("coordinator", coordinator);
        model.addAttribute("professors", professors);

        return "coordinator";
    }

    @RequestMapping("/new-project")
    public ModelAndView createNewProject()
    {
        return new ModelAndView("new-project","command", new Project());
    }

    @RequestMapping(value = "/professors/new", method = RequestMethod.GET)
    public String newProfessor(Model model) {
        model.addAttribute("professorForm", new Professor());
        return "professor/new";
    }

    @RequestMapping(value = "/professors", method = RequestMethod.POST)
    public String createProfessor(@ModelAttribute("professorForm") Professor professorForm,
                                  BindingResult bindingResult,
                                  Model model,
                                  RedirectAttributes redirectAttributes,
                                  HttpServletResponse response) {
        professorValidator.validate(professorForm, bindingResult);
        if (bindingResult.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "professor/new";
        }
        professorRepository.save(professorForm);

        redirectAttributes.addFlashAttribute("message","Professor succesfully created");
        return "redirect:/facultyMenu";
    }

    // Have to get the professor's ID
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

    // Not currently working. Need to change the confirmation workflow
    @RequestMapping(value = "/save-student", method = RequestMethod.POST)
    public ModelAndView addStudent(@AuthenticationPrincipal UserDetails currentUser, @RequestParam("id") Long id)
    {
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());
        Professor professor = authenticatedUser.getProfessor();
        student = studentRepository.findOne(id);

        professorRepository.save(professor);

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
        project.setProjectProf(null);

        projectRepository.delete(project);

        return new ModelAndView("redirect:/facultyMenu");
    }
}
