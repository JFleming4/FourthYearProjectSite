package app.models;
import org.junit.Before;
import org.junit.Test;

public class TimeSlotTest {
    private TimeSlot ts1, ts2;
    private Project project;

    @Before
    public void setup() {
        ts1 = new TimeSlot(Day.MONDAY, 8, 0);
        ts2 = new TimeSlot(Day.FRIDAY, 14, 30);
        project = new Project();
    }

    @Test
    public void testCompareTo() {
        assert ts1.compareTo(ts2) < 0;
        assert ts1.compareTo(ts1) == 0;
        assert ts2.compareTo(ts1) > 0;
    }

    @Test
    public void testEquals() {
        assert ts1.equals(ts1);
        assert !ts1.equals(ts2);
        ts1.setProject(project);
        assert ts1.equals(ts1);
    }

    @Test
    public void getSetId() {
        ts1.setId(new Long(5));
        assert ts1.getId().equals(new Long(5));
    }

    @Test
    public void getSetSelected() {
        ts1.setSelected(2);
        assert ts1.getSelected() == 2;
    }

    @Test
    public void getSetDay() {
        ts1.setDay(Day.WEDNESDAY);
        assert ts1.getDay() == Day.WEDNESDAY;
    }

    @Test
    public void getSetStartHour() {
        ts1.setStartHour(18);
        assert ts1.getStartHour() == 18;
    }

    @Test
    public void getSetStartMinute() {
        ts1.setStartMinute(30);
        assert ts1.getStartMinute() == 30;
    }

    @Test
    public void getSetProject() {
        ts1.setProject(project);
        assert ts1.getProject().equals(project);
    }
}
