package app.models;

import javax.persistence.*;

@Entity
public class TimeSlot implements Comparable<TimeSlot>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Day day;
    private int startHour;
    private int startMinute;

    @ManyToOne(cascade = CascadeType.ALL)
    private Project project;

    /***
     * Create an available time slot
     * @param day of the week
     * @param startHour should be 0 - 23
     * @param startMinute should be 0 or 30
     */
    public TimeSlot(Day day, int startHour, int startMinute) {
        this.project = null;
        this.day = day;
        this.startHour = startHour;
        this.startMinute = startMinute;
    }

    /***
     * Create an available time slot
     * @param day of the week
     * @param startTime in 24hour clock i.e. 22:30
     */
    public TimeSlot(Day day, String startTime) {
        this(
                day,
                new Integer(startTime.split(":")[0]),
                new Integer(startTime.split(":")[1])
        );
    }

    /***
     * create a duplicate TimeSlot with a null user
     * @param ts TimeSlot to duplicate
     */
    public TimeSlot(TimeSlot ts) {
        this(ts.day, ts.startHour, ts.startMinute);
    }

    public Day getDay() {
        return this.day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project p) {
        this.project = p;
    }

    public int getStartHour() {
        return this.startHour;
    }

    public void setStartHour(int sh) {
        this.startHour = sh;
    }

    public int getStartMinute() {
        return this.startMinute;
    }

    public void setStartMinute(int sm) {
        this.startMinute = sm;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    /***
     * Compare to other time slots for sorting
     * @param ts the timeslot to be compared
     * @return 1 for greater than, 0 for equal, -1 for less than
     */
    public int compareTo(TimeSlot ts)
    {
        if(this.day == ts.day) {
            if (this.startHour == ts.startHour)
                return this.startMinute - ts.startMinute;
            return this.startHour - ts.startHour;
        }
        return this.day.compareTo(ts.day);
    }

    /***
     * its toString
     * @return the string representation of the object
     */
    public String toString() {
        return this.day + " at " + this.startHour + ":" + this.startMinute;
    }

    /***
     * check equality
     * @param o object to compare
     * @return true if equal, false otherwise
     */
    public boolean equals(Object o) {
        if(o == null) return false;
        if(o instanceof TimeSlot) {
            TimeSlot ts = (TimeSlot) o;
            return ts.day.equals(this.day) && ts.startMinute == this.startMinute &&
                    ts.startHour == this.startHour && this.project.equals(ts.project);
        }
        return false;
    }

}
