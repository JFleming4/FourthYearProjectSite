public class ProjectCoordinator extends Professor {


    public ProjectCoordinator(String firstName, String lastName, String email)
    {
        super(firstName, lastName, email);
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
}
