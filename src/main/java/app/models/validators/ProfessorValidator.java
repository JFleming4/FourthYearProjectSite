package app.models.validators;

import app.models.Professor;
import app.models.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProfessorValidator implements Validator{
    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return Professor.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Professor professor = (Professor) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "profNumber", "NotEmpty");
        if (professorRepository.existsByProfNumber(professor.getProfNumber())) {
            errors.rejectValue("profNumber", "Duplicate.professorForm.profNumber");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
    }
}
