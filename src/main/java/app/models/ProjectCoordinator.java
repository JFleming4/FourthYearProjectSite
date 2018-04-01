package app.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProjectCoordinator extends Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public ProjectCoordinator(String firstName, String lastName, String email)
    {
        super(firstName, lastName, email);
    }

    public ProjectCoordinator() {
        this(null, null, null);
    }

    public void setReportDeadline()
    {

    }

    public void allocateRooms()
    {

    }

    public void searchForStudents()
    {

    }


    /**
     * Compare an unknown object to this ProjectCoordinator object
     * @param obj Unknown object
     * @return Boolean whether or not the objects are the same
     */
    public boolean equals(Object obj)
    {
        if (this == obj) return true;

        if (!(obj instanceof ProjectCoordinator)) return false;

        ProjectCoordinator pjc = (ProjectCoordinator) obj;
        return super.equals(obj);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
