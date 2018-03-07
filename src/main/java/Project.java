import java.util.ArrayList;

public class Project {

    private Professor projectProf;
    private ArrayList<Student> students;

    private String description;
    private ArrayList<String> restrictions;
    private int maxCapacity;
    private int currentCapacity;

    private boolean isArchived;
    private boolean isDeleted;


    public Project(Professor projectProf, String description, ArrayList<String> restrictions, int maxCapacity)
    {
        this.projectProf = projectProf;
        this.students = new ArrayList<>();

        this.description = description;
        this.restrictions = restrictions;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = students.size();

        isArchived = false;
        isDeleted = false;
    }


    /**
     * Attempt to add a student to the project. Check against capacity & restrictions
     * @param student Student to be added
     * @return Boolean whether or not the student was added
     */
    public boolean addStudent(Student student)
    {
        if (currentCapacity < maxCapacity)
        {
            if (!restrictions.contains(student.getProgram()))
            {
                students.add(student);
                currentCapacity++;
                return true;
            }
        }
        return false;
    }


    /**
     * Compare an unknown object to this Project object
     * @param obj Unknown object
     * @return Boolean whether or not the objects are the same
     */
    public boolean equals(Object obj)
    {
        if (this == obj) return true;

        if (!(obj instanceof Project)) return false;

        Project pro = (Project) obj;

        return this.projectProf.equals(pro.projectProf)
                && this.students.equals(pro.students)
                && this.description.equals(pro.description)
                && this.restrictions.equals(pro.restrictions)
                && this.maxCapacity == pro.maxCapacity
                && this.currentCapacity == pro.currentCapacity
                && this.isArchived == pro.isArchived
                && this.isDeleted == pro.isDeleted;
    }

    ///////////////
    // Get & Set //
    ///////////////

    public Professor getProjectProf() {
        return projectProf;
    }

    public void setProjectProf(Professor projectProf) {
        this.projectProf = projectProf;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(ArrayList<String> restrictions) {
        this.restrictions = restrictions;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setIsArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
