public class User {

    private String firstName;
    private String lastName;
    private String email;

    public User(String firstName, String lastName, String email)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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
            return this.firstName.equals(user.getFirstName())
                    && this.lastName.equals(user.getLastName())
                    && this.email.equals(user.getEmail());
        }
        return false;
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
