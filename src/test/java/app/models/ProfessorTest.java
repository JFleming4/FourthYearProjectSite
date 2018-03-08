package app.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ProfessorTest {

    private Professor professor1, professor2;
    private ProjectCoordinator projectCoordinator1, projectCoordinator2;
    private Project project1, project2;

    private ArrayList<String> restrictions;

    private final String FIRST = "FirstName";
    private final String LAST = "LastName";
    private final String EMAIL = "first@last.com";

    private final String NEW_FIRST = "NewFirstName";

    private final String PROG = "SYSC";

    private final String D1 = "Description 1";
    private final String D2 = "Description 2";

    private final int MAX_CAPACITY = 2;


    @Before
    public void setup()
    {
        restrictions = new ArrayList<>();
        restrictions.add(PROG);

        projectCoordinator1 = new ProjectCoordinator(FIRST, LAST, EMAIL);
        projectCoordinator2 = new ProjectCoordinator(NEW_FIRST, LAST, EMAIL);

        professor1 = new Professor(FIRST, LAST, EMAIL, projectCoordinator1);
        professor2 = new Professor(FIRST, LAST, EMAIL, projectCoordinator1);

        project1 = new Project(professor1, D1, restrictions, MAX_CAPACITY);
        project2 = new Project(professor1, D2, restrictions, MAX_CAPACITY);
    }

    @Test
    public void testEquals()
    {
        assert professor1.equals(professor2);
    }

    @Test
    public void testCreateProject()
    {
        professor2.createProject(D2, restrictions, MAX_CAPACITY);
        Project project = professor2.getProjects().get(0);

        assert projectCoordinator1.getProjects().contains(project)
                && professor2.getProjects().size() == 1
                && project.getDescription().equals(D2)
                && project.getRestrictions().equals(restrictions)
                && project.getMaxCapacity() == MAX_CAPACITY;
    }

    @Test
    public void testDeleteProject()
    {
        professor1.createProject(D2, restrictions, MAX_CAPACITY);
        Project project = professor1.getProjects().get(0);

        professor1.deleteProject(project);

        assert !projectCoordinator1.getProjects().contains(project)
                && !professor1.getProjects().contains(project)
                && !project.isArchived();
    }

    @Test
    public void testArchiveProject()
    {
        professor1.createProject(D2, restrictions, MAX_CAPACITY);
        Project project = professor1.getProjects().get(0);

        professor1.archiveProject(project);

        assert projectCoordinator1.getProjects().contains(project)
                && professor1.getProjects().contains(project)
                && project.isArchived();
    }

    @Test
    public void testAddProjectToList()
    {
        professor1.addProjectToList(project1);

        assert professor1.getProjects().size() == 1
                && professor1.getProjects().contains(project1);
    }

    @Test
    public void testRemoveProjectFromList()
    {
        professor1.addProjectToList(project1);
        professor1.removeProjectFromList(project1);

        assert professor1.getProjects().isEmpty()
                && !professor1.getProjects().contains(project1);
    }

    @Test
    public void testAddProjectToSecondReaderList()
    {
        professor1.addProjectToSecondReaderList(project1);

        assert professor1.getSecondReaderProjects().size() == 1
                && professor1.getSecondReaderProjects().contains(project1);
    }

    @Test
    public void testRemoveProjectToSecondReaderList()
    {
        professor1.addProjectToSecondReaderList(project1);
        professor1.removeProjectFromSecondReaderList(project1);

        assert professor1.getSecondReaderProjects().isEmpty()
                && !professor1.getSecondReaderProjects().contains(project1);
    }

    @Test
    public void testGetSetProjects()
    {
        ArrayList<Project> projects = new ArrayList<>();
        projects.add(project1);
        professor1.setProjects(projects);

        assert !professor1.getProjects().isEmpty()
                && professor1.getProjects().contains(project1);
    }

    @Test
    public void testGetSetSecondReaderProjects()
    {
        ArrayList<Project> projects = new ArrayList<>();
        projects.add(project2);
        professor1.setSecondReaderProjects(projects);

        assert !professor1.getSecondReaderProjects().isEmpty()
                && professor1.getSecondReaderProjects().contains(project2);
    }

    @Test
    public void testGetSetProjectCoordinator()
    {
        professor1.setProjectCoordinator(projectCoordinator2);
        assert professor1.getProjectCoordinator().equals(projectCoordinator2);
    }
}
