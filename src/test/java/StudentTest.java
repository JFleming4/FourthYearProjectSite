import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class StudentTest {


    private Student student1, student2;
    private ProjectCoordinator projectCoordinator;
    private Professor professor;
    private Project project1, project2;

    private final String FIRST = "FirstName";
    private final String LAST = "LastName";
    private final String EMAIL = "first@last.com";
    private final double STUD_NO = 123456789;
    private final String PROG = "SYSC";

    private final double NEW_STUD_NO = 987654321;
    private final String NEW_PROG = "ARTS";

    private final String DESCRIPTION = "Project Description";
    private final int MAX_CAPACITY = 3;

    @Before
    public void setup()
    {
        ArrayList<String> noRestrictions = new ArrayList<>();
        ArrayList<String> restrictions = new ArrayList<>();
        restrictions.add(PROG);

        student1 = new Student(FIRST, LAST, EMAIL, STUD_NO, PROG);
        student2 = new Student(FIRST, LAST, EMAIL, STUD_NO, PROG);

        projectCoordinator = new ProjectCoordinator(FIRST, LAST, EMAIL);
        professor = new Professor(FIRST, LAST, EMAIL, projectCoordinator);

        project1 = new Project(professor, DESCRIPTION, noRestrictions, MAX_CAPACITY);
        project2 = new Project(professor, DESCRIPTION, restrictions, MAX_CAPACITY);
    }

    @Test
    public void testEquals()
    {
        assert student1.equals(student2);
    }

    @Test
    public void testJoinValidProject()
    {
        assert student1.joinProject(project1) && student1.getProject().equals(project1);
    }

    @Test
    public void testJoinInvalidProject()
    {
        assert !student2.joinProject(project2);
    }

    @Test
    public void testGetSetStudentNumber()
    {
        student1.setStudentNumber(NEW_STUD_NO);
        assert student1.getStudentNumber() == NEW_STUD_NO;
    }

    @Test
    public void testGetSetProgram()
    {
        student1.setProgram(NEW_PROG);
        assert student1.getProgram().equals(NEW_PROG);
    }

    @Test
    public void testGetSetProject()
    {
        student1.setProject(project2);
        assert student1.getProject().equals(project2);
    }
}
