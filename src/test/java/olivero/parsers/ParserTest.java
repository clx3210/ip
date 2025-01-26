package olivero.parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import olivero.commands.ByeCommand;
import olivero.commands.DeadlineCommand;
import olivero.commands.DeleteCommand;
import olivero.commands.EventCommand;
import olivero.commands.ListCommand;
import olivero.commands.MarkCommand;
import olivero.commands.ToDoCommand;
import olivero.commands.UnMarkCommand;
import olivero.exceptions.CommandParseException;


/**
 * Tests for the Parser member SUTs.
 */
public class ParserTest {

    private static final String MESSAGE_EXPECTED_INVALID_INTEGER = "Did you pass "
            + "in a valid integer? Your input: %s";

    @Test
    public void parse_blankArguments_exceptionThrown() {
        String expected = "W-WHAT?! "
                + "I do not understand what you just said :(";

        CommandParseException exception = assertThrows(
                CommandParseException.class, () -> {
                    Parser parser = new Parser();
                    parser.parse("");
                });
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void parse_unsupportedCommand_exceptionThrown() {
        String expected = "W-WHAT?! "
                + "I do not understand what you just said :(";

        CommandParseException exception = assertThrows(
                CommandParseException.class, () -> {
                    Parser parser = new Parser();
                    parser.parse("todo12u4c14|||??::'''");
                });
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void parse_invalidDeadlineByTokenNoFrontWhitespace_exceptionThrown() {
        String expected = "Did you correctly "
                + "specify the '/by <end date>' of your deadline task?"
                + System.lineSeparator()
                + "Example usage: deadline <description> /by <start date>";
        CommandParseException exception = assertThrows(
                CommandParseException.class, () -> {
                    Parser parser = new Parser();
                    parser.parse("deadline xx/by 202-123-3" + System.lineSeparator());
                });
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void parse_invalidDeadlineByTokenNoBackWhitespace_exceptionThrown() {
        String expected = "Did you correctly "
                + "specify the '/by <end date>' of your deadline task?"
                + System.lineSeparator()
                + "Example usage: deadline <description> /by <start date>";
        CommandParseException exception = assertThrows(
                CommandParseException.class, () -> {
                    Parser parser = new Parser();
                    parser.parse("deadline xx /by202-123-3" + System.lineSeparator());
                });
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void parse_invalidDeadlineByTokenNoWhitespace_exceptionThrown() {
        String expected = "Did you correctly "
                + "specify the '/by <end date>' of your deadline task?"
                + System.lineSeparator()
                + "Example usage: deadline <description> /by <start date>";
        CommandParseException exception = assertThrows(
                CommandParseException.class, () -> {
                    Parser parser = new Parser();
                    parser.parse("deadline xx/by1" + System.lineSeparator());
                });
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void parse_invalidDeadlineByDateFormat_exceptionThrown() {
        String expected = "Oh... Seems like you formatted your date(s) wrongly?"
                + System.lineSeparator()
                + "Correct date format: yyyy-mm-dd HHmm (e.g. 2019-10-15 1800)";

        CommandParseException exception = assertThrows(
                CommandParseException.class, () -> {
                    Parser parser = new Parser();
                    parser.parse("deadline xx /by 202-123-3" + System.lineSeparator());
                });
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void parse_validDeadlineFormat_success() {
        try {
            assertInstanceOf(
                    DeadlineCommand.class,
                    new Parser().parse("deadline do this that /by 2025-1-2 1600"));
        } catch (CommandParseException e) {
            fail();
        }
    }

    @Test
    public void parse_validEventFormat_success() {
        try {
            assertInstanceOf(
                    EventCommand.class,
                    new Parser().parse("event do this "
                            + "that /from 2025-1-2 1600 /to 2025-1-3 000"));
        } catch (CommandParseException e) {
            fail();
        }
    }

    @Test
    public void parse_validTodoFormat_success() {
        try {
            assertInstanceOf(
                    ToDoCommand.class,
                    new Parser().parse("todo read book"));
        } catch (CommandParseException e) {
            fail();
        }
    }

    @Test
    public void parse_validListFormat_success() {
        try {
            assertInstanceOf(
                    ListCommand.class,
                    new Parser().parse("list"));
        } catch (CommandParseException e) {
            fail();
        }
    }

    @Test
    public void parse_validMarkFormat_success() {
        try {
            assertInstanceOf(
                    MarkCommand.class,
                    new Parser().parse("mark 1"));
        } catch (CommandParseException e) {
            fail();
        }
    }

    @Test
    public void parse_invalidMarkTaskNumber_exceptionThrown() {
        String[] invalidArgs = {" ", "   ", " a", " ^b", " -", " 1rhi1c", " @|||@!", "", " abb |"};
        for (String arg : invalidArgs) {
            String expected = String.format(MESSAGE_EXPECTED_INVALID_INTEGER, arg);
            CommandParseException exception = assertThrows(
                    CommandParseException.class, () -> new Parser().parse(String.format("mark%s", arg)));
            assertEquals(expected, exception.getMessage());
        }
    }

    @Test
    public void parse_validUnMarkFormat_success() {
        try {
            assertInstanceOf(
                    UnMarkCommand.class,
                    new Parser().parse("unmark 1"));
        } catch (CommandParseException e) {
            fail();
        }
    }

    @Test
    public void parse_invalidUnMarkTaskNumber_exceptionThrown() {
        String[] invalidArgs = {" ", "   ", " asdsasd", " ^b", " -", " 1rhi1c", " @|||@!", "", " a"};
        for (String arg : invalidArgs) {
            String expected = String.format(MESSAGE_EXPECTED_INVALID_INTEGER, arg);
            CommandParseException exception = assertThrows(
                    CommandParseException.class, () -> new Parser().parse(String.format("unmark%s", arg)));
            assertEquals(expected, exception.getMessage());
        }
    }

    @Test
    public void parse_validDeleteFormat_success() {
        try {
            assertInstanceOf(
                    DeleteCommand.class,
                    new Parser().parse("delete 1"));
        } catch (CommandParseException e) {
            fail();
        }
    }

    @Test
    public void parse_invalidDeleteTaskNumber_exceptionThrown() {
        String[] invalidArgs = {" asd", " b", " -", " 1rhi1c", " @|||@!", "", " ", "  "};
        for (String arg : invalidArgs) {
            String expected = String.format(MESSAGE_EXPECTED_INVALID_INTEGER, arg);
            CommandParseException exception = assertThrows(
                    CommandParseException.class, () -> new Parser().parse(String.format("delete%s", arg)));
            assertEquals(expected, exception.getMessage());
        }
    }

    @Test
    public void parse_validByeFormat_success() {
        try {
            assertInstanceOf(
                    ByeCommand.class,
                    new Parser().parse("bye"));
        } catch (CommandParseException e) {
            fail();
        }
    }
}
