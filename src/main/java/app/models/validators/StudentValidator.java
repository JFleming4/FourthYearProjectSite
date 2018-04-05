package app.models.validators;

import app.models.Student;
import app.models.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class StudentValidator implements Validator{
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return Student.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Student student = (Student) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studentNumber", "NotEmpty");
        if (studentRepository.existsByStudentNumber(student.getStudentNumber())) {
            errors.rejectValue("studentNumber", "Duplicate.studentForm.studentNumber");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "program", "NotEmpty");
    }
}
