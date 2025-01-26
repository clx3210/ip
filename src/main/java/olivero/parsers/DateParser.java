package olivero.parsers;


import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * Provides utility members for datetime parsing, along with fixed formats
 * for datetime processing and displaying.
 */
public final class DateParser {



    /** Fixed format for all datetime data processing. */
    public static final DateTimeFormatter INPUT_DATE_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-M-d Hmm");

    /** Fixed format for displaying datetime objects onto the ui. */
    public static final DateTimeFormatter DISPLAY_DATE_FORMATTER = DateTimeFormatter
            .ofPattern("MMM dd yyyy HHmm");

    private DateParser() {
    }

    /**
     * Parses the given input date string into a {@code LocalDateTime} object
     * based on formatting of {@link #INPUT_DATE_FORMATTER}, and returns the parsed object.
     *
     * @param dateString The datetime string to be parsed.
     * @return The parsed {@code LocalDateTime} object.
     * @throws DateTimeParseException If the format of the provided {@code dateString}
     *                                is invalid or does not match {@link #INPUT_DATE_FORMATTER}.
     */
    public static LocalDateTime parseInputDate(String dateString) throws DateTimeParseException {
        return LocalDateTime.parse(dateString, INPUT_DATE_FORMATTER);
    }

    /**
     * Returns the string representation of the provided {@code LocalDateTime} object
     * formatted using {@link #DISPLAY_DATE_FORMATTER}.
     *
     * @param date The {@code LocalDateTime} object to be formatted into a string.
     * @return The formatted string of the given date.
     * @throws DateTimeException If an error occurs during formatting
     */
    public static String asDisplayDateString(LocalDateTime date) {
        return date.format(DISPLAY_DATE_FORMATTER);
    }

    /**
     * Returns the string representation of the provided {@code LocalDateTime} object
     * formatted using {@link #INPUT_DATE_FORMATTER}.
     *
     * @param date The {@code LocalDateTime} object to be formatted into a string.
     * @return The formatted string of the given date.
     * @throws DateTimeException If an error occurs during formatting
     */
    public static String asInputDateString(LocalDateTime date) {
        return date.format(INPUT_DATE_FORMATTER);
    }
}
