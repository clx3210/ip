package olivero.parsers;

import static olivero.parsers.ParserTestUtil.MESSAGE_DELETE_INVALID;
import static olivero.parsers.ParserTestUtil.MESSAGE_EXPECTED_INVALID_INTEGER;
import static olivero.parsers.ParserTestUtil.MESSAGE_MARK_INVALID;
import static olivero.parsers.ParserTestUtil.MESSAGE_UNMARK_INVALID;
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
import olivero.commands.TodoCommand;
import olivero.commands.UnMarkCommand;
import olivero.exceptions.CommandParseException;


/**
 * Tests for the Parser member SUTs.
 */
public class ParserTest {


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
        String expected = String.join(System.lineSeparator(),
                DeadlineCommand.MESSAGE_INVALID_FORMAT, DeadlineCommand.MESSAGE_USAGE);
        CommandParseException exception = assertThrows(
                CommandParseException.class, () -> {
                    new Parser().parseCommand("deadline xx/by 1");
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
                    new Parser().parseCommand("deadline xx /by 202-123-3");
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
                    TodoCommand.class,
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
        CommandParseException parseException = assertThrows(
                CommandParseException.class, () ->
                        new Parser().parseCommand("mark"));
        assertEquals(MESSAGE_MARK_INVALID, parseException.getMessage());

        String[] invalidArgs = {" ", "   ", "", " a", " ^b", "99999999999999999999", " 1rhi1c", " @|||@!", " abb |"};
        for (String arg : invalidArgs) {
            String expected = String.format(MESSAGE_EXPECTED_INVALID_INTEGER, arg.trim());

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
        CommandParseException parseException = assertThrows(
                CommandParseException.class, () ->
                        new Parser().parseCommand("unmark"));
        assertEquals(MESSAGE_UNMARK_INVALID, parseException.getMessage());

        String[] invalidArgs = {" ", "   ", "", " asdsasd", "-q", " 1rhi1c", " @|||@!", "99999999999999999999"};
        for (String arg : invalidArgs) {
            String expected = String.format(MESSAGE_EXPECTED_INVALID_INTEGER, arg.trim());
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
    public void parseCommand_invalidDeleteArgument_exceptionThrown() {
        CommandParseException parseException = assertThrows(
                CommandParseException.class, () ->
                        new Parser().parseCommand("delete"));
        assertEquals(MESSAGE_DELETE_INVALID, parseException.getMessage());

        String[] invalidArgs = {" ", "", "   ", "asd", "b", "-", "1rhi1c", "@|||@!", "99999999999999999999"};
        for (String arg : invalidArgs) {
            String expected = String.format(MESSAGE_EXPECTED_INVALID_INTEGER, arg.trim());
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
