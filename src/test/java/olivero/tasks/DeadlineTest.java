package olivero.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DeadlineTest {
    @Test
    public void toFormattedString_formatEqualsExpected_success() {
        Deadline deadline = new Deadline("submit report", LocalDateTime.of(2021, 9, 17, 18, 0), false);
        assertEquals("D | 0 | submit report | 2021-9-17 1800", deadline.toFormattedString());
    }

    @Test
    public void toString_descriptionFormatEqualsExpectedFormat_success() {
        Deadline deadline = new Deadline("submit report", LocalDateTime.of(2021, 9, 17, 18, 0), true);
        assertEquals("[D][X] submit report (by: Sep 17 2021 1800)", deadline.toString());
    }

    @Test
    public void getDescription_descriptionEqualsExpectedDescription_success() {
        Deadline deadline = new Deadline("submit report", LocalDateTime.of(2021, 9, 17, 18, 0), true);
        assertEquals("submit report", deadline.getDescription());
    }

    @Test
    public void isDone_isDoneEqualsExpectedIsDone_success() {
        Deadline deadline = new Deadline("submit report", LocalDateTime.of(2021, 9, 17, 18, 0), true);
        assertTrue(deadline.isDone());
    }

    @Test
    public void setDone_setDoneToFalse_success() {
        Deadline deadline = new Deadline("submit report", LocalDateTime.of(2021, 9, 17, 18, 0), true);
        deadline.setDone(false);
        assertFalse(deadline.isDone());
    }

}
