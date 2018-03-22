package app.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import static app.models.FileAttachment.FileAttachmentType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ProjectTest {

    private Project project1, project2;
    private ProjectCoordinator projectCoordinator;
    private Professor professor1, professor2;
    private Student student1, student2, student3;

    private final String FIRST = "FirstName";
    private final String LAST = "LastName";
    private final String EMAIL = "first@last.com";

    private final String NEW_FIRST = "NewFirstName";
    private final String NEW_LAST = "NewLastName";
    private final String NEW_EMAIL = "new_first@last.com";

    private final String PROG = "SYSC";
    private final String REST_PROG = "ARTS";
    private final String AAALRIGHT = "AAAAAAAAAAAAAAAAAlright";

    private final String STUD_NO1 = "1";
    private final String STUD_NO2 = "2";
    private final String STUD_NO3 = "3";

    private final String DESCRIPTION = "Project Description";
    private final String NEW_DESC = "New Description";

    private final int MAX_CAPACITY = 2;
    private final int NEW_CAPACITY = 3;

    private ArrayList<String> restrictions;


    @Before
    public void setup()
    {
        restrictions = new ArrayList<>();
        restrictions.add(REST_PROG);

        projectCoordinator = new ProjectCoordinator(FIRST, LAST, EMAIL);

        professor1 = new Professor(FIRST, LAST, EMAIL, projectCoordinator);
        professor2 = new Professor(NEW_FIRST, NEW_LAST, NEW_EMAIL, projectCoordinator);

        project1 = new Project(professor1, DESCRIPTION, restrictions, MAX_CAPACITY);
        project2 = new Project(professor1, DESCRIPTION, restrictions, MAX_CAPACITY);

        student1 = new Student(FIRST, LAST, EMAIL, STUD_NO1, PROG);
        student2 = new Student(FIRST, LAST, EMAIL, STUD_NO2, PROG);
        student3 = new Student(FIRST, LAST, EMAIL, STUD_NO3, PROG);
    }


    @Test
    public void testEquals()
    {
        assert project1.equals(project2);
    }

    @Test
    public void testAddStudent()
    {

        assert project1.addStudent(student1)
                && project1.addStudent(student2)
                && !project1.addStudent(student3);
    }

    @Test
    public void testAddRestriction()
    {
        project1.addRestriction(AAALRIGHT);
        assert project1.getRestrictions().get(0).equals(AAALRIGHT)
                && project1.getRestrictions().get(1).equals(REST_PROG);
    }

    @Test
    public void testGetSetProjectProf()
    {
        project1.setProjectProf(professor2);
        assert project1.getProjectProf().equals(professor2);
    }

    @Test
    public void testGetSetStudents()
    {
        ArrayList<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);

        project1.setStudents(students);
        assert project1.getStudents().equals(students);
    }

    @Test
    public void testGetSetDescription()
    {
        project1.setDescription(NEW_DESC);
        assert project1.getDescription().equals(NEW_DESC);
    }

    @Test
    public void testGetSetRestrictions()
    {
        ArrayList<String> newRestrictions = new ArrayList<>();
        newRestrictions.add(PROG);

        project1.setRestrictions(newRestrictions);
        assert project1.getRestrictions().equals(newRestrictions);
    }

    @Test
    public void testGetSetMaxCapacity()
    {
        project1.setMaxCapacity(NEW_CAPACITY);
        assert project1.getMaxCapacity() == NEW_CAPACITY;
    }

    @Test
    public void testGetSetCurrentCapacity()
    {
        project1.setCurrentCapacity(MAX_CAPACITY);
        assert project1.getCurrentCapacity() == MAX_CAPACITY;
    }

    @Test
    public void testGetSetIsArchived()
    {
        project1.setIsArchived(true);
        assert project1.isArchived();
    }

    @Test
    public void getProposal()
    {
        FileAttachment proposal = new FileAttachment("file.txt", FileAttachmentType.PROPOSAL, project1);
        assertEquals(proposal, project1.getProposal());
        assertNull(project1.getReport());
    }

    @Test
    public void getReport()
    {
        FileAttachment report = new FileAttachment("file.txt", FileAttachmentType.FINAL_REPORT, project1);
        assertEquals(report, project1.getReport());
        assertNull(project1.getProposal());
    }
}
