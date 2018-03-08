package app.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Project implements Comparable<Project> {

    @ManyToOne
    private Professor projectProf;

    @ManyToOne
    private Professor secondReader;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Student> students;

    private String description;

    @Transient
    private List<String> restrictions;
    private int maxCapacity;
    private int currentCapacity;

    private boolean isArchived;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    public Project(Professor projectProf, String description, List<String> restrictions, int maxCapacity)
    {
        this.projectProf = projectProf;
        this.students = new ArrayList<>();

        this.description = description;
        this.restrictions = restrictions;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = students.size();

        isArchived = false;
    }

    public Project() {}

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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<String> restrictions) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Professor getSecondReader() {
        return secondReader;
    }

    public void setSecondReader(Professor secondReader) {
        this.secondReader = secondReader;
    }
}