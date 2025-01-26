package olivero.parsers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateParser {



    public static final DateTimeFormatter INPUT_DATE_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-M-d Hmm");
    public static final DateTimeFormatter DISPLAY_DATE_FORMATTER = DateTimeFormatter
            .ofPattern("MMM dd yyyy HHmm");

    private DateParser() {
    }

    public static LocalDateTime parseInputDate(String dateString) throws DateTimeParseException {
        return LocalDateTime.parse(dateString, INPUT_DATE_FORMATTER);
    }

    public static String asDisplayDateString(LocalDateTime date) {
        return date.format(DISPLAY_DATE_FORMATTER);
    }

    public static String asInputDateString(LocalDateTime date) {
        return date.format(INPUT_DATE_FORMATTER);
    }
}
