public class Student extends User {

    private int studentNumber;
    private String program;

    private Project project;

    public Student(String firstName, String lastName, String email, int studentNumber, String program)
    {
        super(firstName, lastName, email);
        this.studentNumber = studentNumber;
        this.program = program;
    }

    public void searchForProjects(String searchPhrase)
    {

    }

    /**
     * Attempt to join a project
     * @param project The project they want to join
     */
    public void joinProject(Project project)
    {
        if (project.addStudent(this))
            this.project = project;
        else
            System.out.println("Unable to join this project"); // (for now)
    }



    private void submitFinalReport()
    {

    }

    /**
     * Compare an unknown object to this Student object
     * @param obj Unknown object
     * @return Boolean whether or not the objects are the same
     */
    public boolean equals(Object obj)
    {
        if (this == obj) return true;

        if (!(obj instanceof Student)) return false;

        Student stud = (Student) obj;

        return super.equals(obj)
                && (this.studentNumber == stud.getStudentNumber())
                && (this.program.equals(stud.getProgram()))
                && (this.project.equals(stud.getProject()));
    }


    ///////////////
    // Get & Set //
    ///////////////

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
