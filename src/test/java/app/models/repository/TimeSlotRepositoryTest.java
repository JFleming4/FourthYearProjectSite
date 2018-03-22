package app.models.repository;
import app.Application;
import app.TestConfig;
import app.models.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = { TestConfig.class })
@Transactional
public class TimeSlotRepositoryTest {
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    private TimeSlot ts;

    @Before
    public void setup() {
        try {
            ts = new TimeSlot(Day.MONDAY, 8, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTimeSlot() {
        timeSlotRepository.save(ts);
        TimeSlot time = null;
        try {
            time = timeSlotRepository.findOne(ts.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(ts, time);
    }
}
