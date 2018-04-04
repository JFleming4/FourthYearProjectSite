package app.models;

import app.models.repository.ProfessorRepository;
import app.models.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AuthenticatedUserValidator implements Validator{
    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return AuthenticatedUser.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (authenticatedUser.getUsername().length() < 6 || authenticatedUser.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (authenticatedUserService.findByUsername(authenticatedUser.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (authenticatedUser.getPassword().length() < 8 || authenticatedUser.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!authenticatedUser.getPasswordConfirm().equals(authenticatedUser.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }

        // Check if valid record exists to attach this account
        String type = authenticatedUser.getType();
        String number = authenticatedUser.getNumber();

            if(type.equals("Student") && !studentRepository.existsByStudentNumber(number)) {
            errors.rejectValue("number", "Invalid.userForm.studentNumber");
        }

        if((type.equals("Professor") || type.equals("Coordinator")) && !professorRepository.existsByProfNumber(number)) {
            errors.rejectValue("number", "Invalid.userForm.professorNumber");
        }

        if(type.equals("Coordinator") && !(professorRepository.findByProfNumber(number) instanceof ProjectCoordinator)) {
            errors.rejectValue("number", "Invalid.userForm.professorNumber.coordinator");
        }

        if(!(type.equals("Student") || type.equals("Professor") || type.equals("Coordinator"))) {
            errors.rejectValue("type", "Invalid.userForm.type");
        }
    }
}
