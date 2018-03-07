import java.util.ArrayList;

public class Professor extends User {

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
     * the Project Coordinator.
     * @param description Project description
     * @param restrictions Project restrictions
     * @param studentCapacity Project capacity (how many students can join)
     */
    public void createProject(String description, ArrayList<String> restrictions, int studentCapacity)
    {
        Project project = new Project(this, description, restrictions, studentCapacity);

        projectCoordinator.addProjectToList(project);
        this.addProjectToList(project);
    }


    /**
     * Delete the project, removing it from the Professor's and the Project Coordinator's
     * list of proejcts.
     * @param project The project to delete
     */
    public void deleteProject(Project project)
    {
        if (projects.contains(project))
        {
            projectCoordinator.removeProjectFromList(project); // Should we do this?
            this.removeProjectFromList(project);

            project.setIsDeleted(true);
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
                && ((this.secondReaderProjects == null) || (this.secondReaderProjects.equals(prof.secondReaderProjects)))
                && ((this.projectCoordinator == null) || (this.projectCoordinator.equals(prof.projectCoordinator)));
    }


    protected void addProjectToList(Project project) {
        projects.add(project);
    }

    protected void removeProjectFromList(Project project) {
        projects.remove(project);
    }

    protected void addProjectToSecondReaderList(Project project) {
        secondReaderProjects.add(project);
    }

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
