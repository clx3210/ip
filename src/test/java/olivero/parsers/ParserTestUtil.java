package olivero.parsers;

/**
 * Provides utility members for parser unit tests.
 */
public final class ParserTestUtil {
    public static final String MESSAGE_EXPECTED_INVALID_INTEGER = "Did you pass "
            + "in a valid integer? Your input: %s";

    public static final String MESSAGE_DELETE_INVALID = "Your delete command format is invalid..."
            + System.lineSeparator()
            + "Usages:"
            + System.lineSeparator()
            + "1. delete <taskNumber>"
            + System.lineSeparator()
            + "2. delete -m <taskNo 1> <taskNo 2> ... <taskNo K>"
            + System.lineSeparator()
            + "3. delete -m <startTaskNo>-<endTaskNo>";

    public static final String MESSAGE_UNMARK_INVALID = "Your unmark command format is invalid..."
            + System.lineSeparator()
            + "Usages:"
            + System.lineSeparator()
            + "1. unmark <task number>"
            + System.lineSeparator()
            + "2. unmark -m <taskNo 1> <taskNo 2> ... <taskNo K>"
            + System.lineSeparator()
            + "3. unmark -m <startTaskNo>-<endTaskNo>";

    public static final String MESSAGE_MARK_INVALID = "Your mark command format is invalid..."
            + System.lineSeparator()
            + "Usages:"
            + System.lineSeparator()
            + "1. mark <task number>"
            + System.lineSeparator()
            + "2. mark -m <taskNo 1> <taskNo 2> ... <taskNo K>"
            + System.lineSeparator()
            + "3. mark -m <startTaskNo>-<endTaskNo>";
}
