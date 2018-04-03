package app.models;

import java.util.List;

public class TimeSlotRequest {
    private Long id;
    private List<TimeSlot> timeSlots;

    public TimeSlotRequest(Long id, List<TimeSlot> timeSlots) {
        this.id = id;
        this.timeSlots = timeSlots;
    }
    public TimeSlotRequest() {
        this(null, null);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        System.out.println("Setting list");
        System.out.println(timeSlots);
        this.timeSlots = timeSlots;
    }

    public String toString() {
        String val = "id: " + id + "TimeSlots: ";
        for(TimeSlot t: timeSlots) {
            val += "tId: " + t.getId() + " Selected: " + t.getSelected() + ", ";
        }
        return val;
    }
}
