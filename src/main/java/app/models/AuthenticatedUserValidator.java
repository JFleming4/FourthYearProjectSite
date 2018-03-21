package app.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AuthenticatedUserValidator implements Validator{
    @Autowired
    private AuthenticatedUserService authenticatedUserService;

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
    }
}
