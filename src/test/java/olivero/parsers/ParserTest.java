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

    private static final String MESSAGE_DELETE_INVALID = "Your delete command format is invalid..."
            + System.lineSeparator()
            + "Usages:"
            + System.lineSeparator()
            + "1. delete <taskNumber>"
            + System.lineSeparator()
            + "2. delete -m <taskNo 1> <taskNo 2> ... <taskNo K>"
            + System.lineSeparator()
            + "3. delete -m <startTaskNo>-<endTaskNo>";

    private static final String MESSAGE_UNMARK_INVALID = "Your unmark command format is invalid..."
            + System.lineSeparator()
            + "Usages:"
            + System.lineSeparator()
            + "1. unmark <task number>"
            + System.lineSeparator()
            + "2. unmark -m <taskNo 1> <taskNo 2> ... <taskNo K>"
            + System.lineSeparator()
            + "3. unmark -m <startTaskNo>-<endTaskNo>";

    private static final String MESSAGE_MARK_INVALID = "Your mark command format is invalid..."
            + System.lineSeparator()
            + "Usages:"
            + System.lineSeparator()
            + "1. mark <task number>"
            + System.lineSeparator()
            + "2. mark -m <taskNo 1> <taskNo 2> ... <taskNo K>"
            + System.lineSeparator()
            + "3. mark -m <startTaskNo>-<endTaskNo>";
    @Test
    public void parseCommand_unsupportedCommand_exceptionThrown() {
        String expected = "W-WHAT?! "
                + "I do not understand what you just said :(";

        CommandParseException exception = assertThrows(
                CommandParseException.class, () -> {
                    new Parser().parseCommand("todo12u4c14|||??::'''");
                });
        assertEquals(expected, exception.getMessage());
    }
    @Test
    public void parseCommand_invalidDeadlineSyntax_exceptionThrown() {
        String expected = "Your deadline command format is invalid..."
                + System.lineSeparator()
                + "Example usage: deadline <description> /by <start date>";
        CommandParseException exception = assertThrows(
                CommandParseException.class, () -> {
                    new Parser().parseCommand("deadline xx/by 1" + System.lineSeparator());
                });
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void parseCommand_invalidDeadlineDateFormat_exceptionThrown() {
        String expected = "Oh... Seems like you formatted your date(s) wrongly?"
                + System.lineSeparator()
                + "Correct date format: yyyy-m-d Hmm (e.g. 2019-10-15 1800)";

        CommandParseException exception = assertThrows(
                CommandParseException.class, () -> {
                    new Parser().parseCommand("deadline xx /by 202-123-3" + System.lineSeparator());
                });
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void parseCommand_validDeadlineFormat_success() {
        try {
            assertInstanceOf(
                    DeadlineCommand.class,
                    new Parser().parseCommand("deadline do this that /by 2025-1-2 1600"));
        } catch (CommandParseException e) {
            fail();
        }
    }

    @Test
    public void parseCommand_validEventFormat_success() {
        try {
            assertInstanceOf(
                    EventCommand.class,
                    new Parser().parseCommand("event do this "
                            + "that /from 2025-1-2 1600 /to 2025-1-3 000"));
        } catch (CommandParseException e) {
            fail();
        }
    }

    @Test
    public void parseCommand_validTodoFormat_success() {
        try {
            assertInstanceOf(
                    ToDoCommand.class,
                    new Parser().parseCommand("todo read book"));
        } catch (CommandParseException e) {
            fail();
        }
    }

    @Test
    public void parseCommand_validListFormat_success() {
        try {
            assertInstanceOf(
                    ListCommand.class,
                    new Parser().parseCommand("list"));
        } catch (CommandParseException e) {
            fail();
        }
    }

    @Test
    public void parseCommand_validMarkFormat_success() {
        try {
            assertInstanceOf(
                    MarkCommand.class,
                    new Parser().parseCommand("mark 1"));

            assertInstanceOf(
                    MarkCommand.class,
                    new Parser().parseCommand("mark -m 1-5"));

            assertInstanceOf(
                    MarkCommand.class,
                    new Parser().parseCommand("mark -m 1 5 3 4"));
        } catch (CommandParseException e) {
            fail();
        }
    }

    @Test
    public void parseCommand_invalidMarkArgument_exceptionThrown() {
        String[] invalidArgs = {" ", "   ", "", " a", " ^b", "  -", " 1rhi1c", " @|||@!", " abb |"};

        for (String arg : invalidArgs) {
            CommandParseException exception = assertThrows(
                    CommandParseException.class, () ->
                            new Parser().parseCommand(String.format("mark%s", arg)));
            assertEquals(MESSAGE_MARK_INVALID, exception.getMessage());
        }
    }

    @Test
    public void parseCommand_outOfRangeMarkTaskNumber_exceptionThrown() {
        String[] invalidArgs = {"99999999999999999999", "1231333082902"};
        for (String arg : invalidArgs) {
            String expected = String.format(MESSAGE_EXPECTED_INVALID_INTEGER, arg);
            CommandParseException exception = assertThrows(
                    CommandParseException.class, () ->
                            new Parser().parseCommand(String.format("mark %s", arg)));
            assertEquals(expected, exception.getMessage());
        }
    }

    @Test
    public void parseCommand_validUnMarkFormat_success() {
        try {
            assertInstanceOf(
                    UnMarkCommand.class,
                    new Parser().parseCommand("unmark 1"));

            assertInstanceOf(
                    UnMarkCommand.class,
                    new Parser().parseCommand("unmark -m 1-10"));

            assertInstanceOf(
                    UnMarkCommand.class,
                    new Parser().parseCommand("unmark -m 1 2 3 9 0"));
        } catch (CommandParseException e) {
            fail();
        }
    }

    @Test
    public void parseCommand_invalidUnMarkArgument_exceptionThrown() {
        String[] invalidArgs = {" ", "   ", "", " asdsasd", " ^b", " -", " 1rhi1c", " @|||@!", " a"};

        for (String arg : invalidArgs) {
            CommandParseException exception = assertThrows(
                    CommandParseException.class, () ->
                            new Parser().parseCommand(String.format("unmark%s", arg)));
            assertEquals(MESSAGE_UNMARK_INVALID, exception.getMessage());
        }
    }


    @Test
    public void parseCommand_outOfRangeUnMarkTaskNumber_exceptionThrown() {
        String[] invalidArgs = {"99999999999999999999", "1231333082902"};
        for (String arg : invalidArgs) {
            String expected = String.format(MESSAGE_EXPECTED_INVALID_INTEGER, arg);
            CommandParseException exception = assertThrows(
                    CommandParseException.class, () ->
                            new Parser().parseCommand(String.format("unmark %s", arg)));
            assertEquals(expected, exception.getMessage());
        }
    }

    @Test
    public void parseCommand_validDeleteFormat_success() {
        try {
            assertInstanceOf(
                    DeleteCommand.class,
                    new Parser().parseCommand("delete 1"));
        } catch (CommandParseException e) {
            fail();
        }
    }
    @Test
    public void parseCommand_emptyDeleteArgument_exceptionThrown() {
        String[] invalidArgs = {" ", "   ", "", " asd", " b", " -", " 1rhi1c", " @|||@!"};

        for (String arg : invalidArgs) {
            CommandParseException exception = assertThrows(
                    CommandParseException.class, () ->
                            new Parser().parseCommand(String.format("delete%s", arg)));
            assertEquals(MESSAGE_DELETE_INVALID, exception.getMessage());
        }
    }

    @Test
    public void parseCommand_outOfRangeDeleteTaskNumber_exceptionThrown() {
        String[] invalidArgs = {"99999999999999999999", "1231333082902"};
        for (String arg : invalidArgs) {
            String expected = String.format(MESSAGE_EXPECTED_INVALID_INTEGER, arg);
            CommandParseException exception = assertThrows(
                    CommandParseException.class, () ->
                            new Parser().parseCommand(String.format("delete %s", arg)));
            assertEquals(expected, exception.getMessage());
        }
    }

    @Test
    public void parseCommand_massOpsExceedsMaxTasks_exceptionThrown() {
        String[] invalidArgs = {"-m 1-101", "-m 101-123123", "-m 0-1230"};
        String expected = "Task range is too large!";
        for (String arg : invalidArgs) {

            // unmark
            CommandParseException unmarkException = assertThrows(
                    CommandParseException.class, () ->
                            new Parser().parseCommand(String.format("unmark %s", arg)));
            assertEquals(expected, unmarkException.getMessage());

            // mark
            CommandParseException markException = assertThrows(
                    CommandParseException.class, () ->
                            new Parser().parseCommand(String.format("mark %s", arg)));
            assertEquals(expected, markException.getMessage());

            // delete
            CommandParseException deleteException = assertThrows(
                    CommandParseException.class, () ->
                            new Parser().parseCommand(String.format("delete %s", arg)));

            assertEquals(expected, deleteException.getMessage());
        }
    }

    @Test
    public void parseCommand_validByeFormat_success() {
        try {
            assertInstanceOf(
                    ByeCommand.class,
                    new Parser().parseCommand("bye"));
        } catch (CommandParseException e) {
            fail();
        }
    }
}
