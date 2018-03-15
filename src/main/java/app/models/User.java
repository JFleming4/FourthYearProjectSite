package app.models;

import javax.persistence.CascadeType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
public class User {

    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TimeSlot> timeSlots;

    public User(String firstName, String lastName, String email)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.timeSlots = new ArrayList<>();
    }

    public void enterAvailability()
    {
        // should we add an availability attribute?
    }


    /**
     * Compare an unknown object to this User object
     * @param obj Unknown object
     * @return Boolean whether or not the objects are the same
     */
    public boolean equals(Object obj)
    {
        if (this == obj) return true;

        if (obj != null && obj.getClass() == this.getClass())
        {
            User user = (User) obj;
            return this.firstName.equals(user.firstName)
                    && this.lastName.equals(user.lastName)
                    && this.email.equals(user.email);
        }
        return false;
    }

    /**
     * Add a TimeSlot to the list of availability
     * @param ts the TimeSlot to add
     */
    public void addTimeSlot(TimeSlot ts) {
        if(!timeSlots.contains(ts)) {
            timeSlots.add(ts);
            ts.setUser(this);
        }
    }


    ///////////////
    // Get & Set //
    ///////////////

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
