package app.models;

import java.util.ArrayList;
import java.util.Collections;

public class Professor extends User implements Comparable<Professor> {

    private ArrayList<Project> projects;
    private ArrayList<Project> secondReaderProjects;
    private ProjectCoordinator projectCoordinator;


    public Professor(String firstName, String lastName, String email, ProjectCoordinator projectCoordinator)
    {
        super(firstName, lastName, email);
        this.projects = new ArrayList<>();
        this.secondReaderProjects = new ArrayList<>();
        this.projectCoordinator = projectCoordinator;
    }

    public Professor(String firstName, String lastName, String email)
    {
        this(firstName, lastName, email, null);
    }


    /**
     * Create a new project and add it to the Projects list of the calling Professor and
     * the Project Coordinator. Sort both project lists based on the description.
     * @param description Project description
     * @param restrictions Project restrictions
     * @param studentCapacity Project capacity (how many students can join)
     */
    public void createProject(String description, ArrayList<String> restrictions, int studentCapacity)
    {
        Project project = new Project(this, description, restrictions, studentCapacity);

        projectCoordinator.addProjectToList(project);
        this.addProjectToList(project);

        Collections.sort(projectCoordinator.getProjects());
        Collections.sort(projects);
    }


    /**
     * Delete the project, removing it from the Professor's and the Project Coordinator's
     * list of projects.
     * @param project The project to delete
     */
    public void deleteProject(Project project)
    {
        if (projects.contains(project))
        {
            projectCoordinator.removeProjectFromList(project); // Should we do this?
            this.removeProjectFromList(project);
        }
    }


    /**
     * Archive the project, but keep it in the Professor's list of projects
     * @param project The project to archive
     */
    public void archiveProject(Project project)
    {
        if (projects.contains(project))
            project.setIsArchived(true);
    }


    /**
     * Compare an unknown object to this Professor object
     * @param obj Unknown object
     * @return Boolean whether or not the objects are the same
     */
    public boolean equals(Object obj)
    {
        if (this == obj) return true;

        if (!(obj instanceof Professor)) return false;

        Professor prof = (Professor) obj;

        return super.equals(obj)
                && this.projects.equals(prof.projects)
                && ((this.secondReaderProjects == null && prof.secondReaderProjects == null) || (this.secondReaderProjects.equals(prof.secondReaderProjects)))
                && ((this.projectCoordinator == null && prof.projectCoordinator == null) || (this.projectCoordinator.equals(prof.projectCoordinator)));
    }

    /**
     * Determine the "order" of two professors based on their email address (alphabetical order)
     * @param compareProfessor  Professor you wish to compare against
     * @return Negative (less than), 0 (equal), Positive (greater than)
     */
    public int compareTo(Professor compareProfessor)
    {
        return this.getEmail().compareToIgnoreCase(compareProfessor.getEmail());
    }

    /**
     * Add a project to the Professor's project list. Sort based on project description.
     * @param project Project to add
     */
    protected void addProjectToList(Project project) {
        projects.add(project);
        Collections.sort(projects);
    }

    /**
     * Remove a project from the Professor's project list.
     * @param project Project to remove
     */
    protected void removeProjectFromList(Project project) {
        projects.remove(project);
    }

    /**
     * Add a project to the Professor's second reader list. Sort based on project description.
     * @param project Project to add
     */
    protected void addProjectToSecondReaderList(Project project) {
        secondReaderProjects.add(project);
        Collections.sort(secondReaderProjects);
    }

    /**
     * Remove a project from the Professor's second reader list.
     * @param project Project to remove
     */
    protected void removeProjectFromSecondReaderList(Project project) {
        secondReaderProjects.remove(project);
    }


    ///////////////
    // Get & Set //
    ///////////////

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    public ArrayList<Project> getSecondReaderProjects() {
        return secondReaderProjects;
    }

    public void setSecondReaderProjects(ArrayList<Project> secondReaderProjects) {
        this.secondReaderProjects = secondReaderProjects;
    }

    public ProjectCoordinator getProjectCoordinator() {
        return projectCoordinator;
    }

    public void setProjectCoordinator(ProjectCoordinator projectCoordinator) {
        this.projectCoordinator = projectCoordinator;
    }
}
