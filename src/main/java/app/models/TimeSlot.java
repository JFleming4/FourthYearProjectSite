package app.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TimeSlot implements Comparable<TimeSlot>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Day day;
    private int startHour;
    private int startMinute;
    private User user;

    /***
     * Create an available time slot
     * @param user the user that is associated
     * @param day of the week
     * @param startHour should be 0 - 23
     * @param startMinute should be 0 or 30
     */
    public TimeSlot(User user, Day day, int startHour, int startMinute) {
        this.user = user;
        this.day = day;
        this.startHour = startHour;
        this.startMinute = startMinute;
    }

    /***
     * Create an available time slot
     * @param user the user that is associated
     * @param day of the week
     * @param startTime in 24hour clock i.e. 22:30
     */
    public TimeSlot(User user, Day day, String startTime) {
        this(
                user,
                day,
                new Integer(startTime.split(":")[0]),
                new Integer(startTime.split(":")[1])
        );
    }

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
                    ts.startHour == this.startHour;
        }
        return false;
    }

}
