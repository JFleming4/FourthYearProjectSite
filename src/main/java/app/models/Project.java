package app.models;

import java.util.ArrayList;
import java.util.Collections;

public class Project implements Comparable<Project> {

    private Professor projectProf;
    private ArrayList<Student> students;

    private String description;
    private ArrayList<String> restrictions;
    private int maxCapacity;
    private int currentCapacity;

    private boolean isArchived;


    public Project(Professor projectProf, String description, ArrayList<String> restrictions, int maxCapacity)
    {
        this.projectProf = projectProf;
        this.students = new ArrayList<>();

        this.description = description;
        this.restrictions = restrictions;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = students.size();

        isArchived = false;
    }


    /**
     * Attempt to add a student to the project. Check against capacity & restrictions.
     * Sort the list of students based on their email address.
     * @param student Student to be added
     * @return Boolean whether or not the student was added
     */
    public boolean addStudent(Student student)
    {
        if (!isArchived && currentCapacity < maxCapacity)
        {
            if (!restrictions.contains(student.getProgram()))
            {
                students.add(student);
                currentCapacity++;
                Collections.sort(students);
                return true;
            }
        }
        return false;
    }

    /**
     * Add a restriction to the list of restrictions. Sort based on alphabetical order.
     * @param restriction New restriction
     */
    public void addRestriction(String restriction)
    {
        restrictions.add(restriction);
        Collections.sort(restrictions);
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
                && this.isArchived == pro.isArchived;
    }



    /**
     * Determine the "order" of two projects based on their descriptions (alphabetical order)
     * @param compareProject Project you wish to compare against
     * @return Negative (less than), 0 (equal), Positive (greater than)
     */
    public int compareTo(Project compareProject)
    {
        return this.description.compareToIgnoreCase(compareProject.description);
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

}
