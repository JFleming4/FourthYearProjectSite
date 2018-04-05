package app.controllers;

import app.models.AuthenticatedUser;
import app.models.Student;
import app.models.repository.AuthenticatedUserRepository;
import app.models.repository.StudentRepository;
import app.models.validators.StudentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AuthenticatedUserRepository authenticatedUserService;

    @Autowired
    private StudentValidator studentValidator;

    @GetMapping("/studentMenu")
    public String student(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        AuthenticatedUser authenticatedUser = authenticatedUserService.findByUsername(currentUser.getUsername());
        Student student = authenticatedUser.getStudent();

        model.addAttribute("student", student);
        model.addAttribute("project", student.getProject());

        return "student";
    }

    @RequestMapping(value = "/students/new", method = RequestMethod.GET)
    public String newStudent(Model model) {
        model.addAttribute("studentForm", new Student(null, null, null, null, null));
        return "student/new";
    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    public String createStudent(@ModelAttribute("studentForm") Student studentForm,
                                  BindingResult bindingResult,
                                  Model model,
                                  RedirectAttributes redirectAttributes,
                                  HttpServletResponse response) {
        studentValidator.validate(studentForm, bindingResult);
        if (bindingResult.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "student/new";
        }
        studentRepository.save(studentForm);

        redirectAttributes.addFlashAttribute("message","Student succesfully created");
        return "redirect:/facultyMenu";
    }

}
