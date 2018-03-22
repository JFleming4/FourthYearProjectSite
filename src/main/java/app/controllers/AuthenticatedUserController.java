package app.controllers;

import app.models.*;
import app.models.repository.AuthenticatedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticatedUserController {
    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AuthenticatedUserValidator authenticatedUserValidator;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticatedUserRepository authenticatedUserRepository;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new AuthenticatedUser());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") AuthenticatedUser userForm, BindingResult bindingResult, Model model) {
        authenticatedUserValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        authenticatedUserService.save(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(currentUser.getUsername());
        AuthenticatedUser authenticatedUser = authenticatedUserRepository.findFirstByOrderById();
        model.addAttribute("currentUser", userDetails);
        return "welcome";
    }
}
