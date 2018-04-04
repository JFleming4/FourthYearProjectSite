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
    private boolean selected;

    @ManyToOne
    private Project project;

    /***
     * Create an available time slot
     * @param day of the week
     * @param startHour should be 0 - 23
     * @param startMinute should be 0 or 30
     */
    public TimeSlot(Day day, int startHour, int startMinute) throws Exception {
        this.project = null;
        this.day = day;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.selected = false;
        if(startHour > 18 || startHour < 0) throw new Exception();
        if(startMinute != 0 && startMinute != 30) throw new Exception();

    }
    public TimeSlot(Long id, boolean selected) throws Exception {
        this(null, 8, 0);
        this.selected = selected;
        this.id = id;
    }

    /***
     * Create an available time slot
     */
    public TimeSlot() throws Exception {
        this(
                null,
                8,
                0
        );
    }

    /***
     * create a duplicate TimeSlot with a null project
     * @param ts TimeSlot to duplicate
     */
    public TimeSlot(TimeSlot ts) throws Exception {
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

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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
        if (this == o) return true;
        if(o == null) return false;
        if(o instanceof TimeSlot) {
            TimeSlot ts = (TimeSlot) o;
//            if((this.project != null && ts.project == null) ||
//                    (this.project == null && ts.project != null)) return false;
//            if(this.project != null && (this.project.getId() != ts.project.getId())) return false;
            return ts.day.equals(this.day) &&
                    ts.startMinute == this.startMinute &&
                    ts.startHour == this.startHour &&
                    ((this.project == null && ts.project == null) ||
                                (this.project != null && this.project.equals(ts.project)));
        }
        return false;
    }

}
