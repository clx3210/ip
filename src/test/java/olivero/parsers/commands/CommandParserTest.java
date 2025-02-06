package olivero.parsers.commands;

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
import olivero.parsers.Parser;


/**
 * Tests for the Parser member SUTs.
 */
public class CommandParserTest {

    private static final String MESSAGE_EXPECTED_INVALID_INTEGER = "Did you pass "
            + "in a valid integer? Your input: %s";

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
                + "Correct date format: yyyy-mm-dd HHmm (e.g. 2019-10-15 1800)";

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
        } catch (CommandParseException e) {
            fail();
        }
    }

    @Test
    public void parseCommand_emptyMarkArgument_exceptionThrown() {
        String[] invalidArgs = {" ", "   ", ""};
        String markInvalid = "Your mark command format is invalid..."
                + System.lineSeparator()
                + "Usage: mark <task number>";
        for (String arg : invalidArgs) {
            String expected = String.format(markInvalid, arg);
            CommandParseException exception = assertThrows(
                    CommandParseException.class, () -> new Parser().parseCommand(String.format("mark%s", arg)));
            assertEquals(expected, exception.getMessage());
        }
    }

    @Test
    public void parseCommand_invalidMarkTaskNumber_exceptionThrown() {
        String[] invalidArgs = {"a", "^b", "-", "1rhi1c", "@|||@!", "abb |"};
        for (String arg : invalidArgs) {
            String expected = String.format(MESSAGE_EXPECTED_INVALID_INTEGER, arg);
            CommandParseException exception = assertThrows(
                    CommandParseException.class, () -> new Parser().parseCommand(String.format("mark %s", arg)));
            assertEquals(expected, exception.getMessage());
        }
    }

    @Test
    public void parseCommand_validUnMarkFormat_success() {
        try {
            assertInstanceOf(
                    UnMarkCommand.class,
                    new Parser().parseCommand("unmark 1"));
        } catch (CommandParseException e) {
            fail();
        }
    }

    @Test
    public void parseCommand_emptyUnMarkArgument_exceptionThrown() {
        String[] invalidArgs = {" ", "   ", ""};
        String unmarkInvalid = "Your unmark command format is invalid..."
                + System.lineSeparator()
                + "Usage: unmark <taskNumber>";
        for (String arg : invalidArgs) {
            String expected = String.format(unmarkInvalid, arg);
            CommandParseException exception = assertThrows(
                    CommandParseException.class,
                    () -> new Parser().parseCommand(String.format("unmark%s", arg)));
            assertEquals(expected, exception.getMessage());
        }
    }


    @Test
    public void parseCommand_invalidUnMarkTaskNumber_exceptionThrown() {
        String[] invalidArgs = {"99999999999999999999", "asdsasd", "^b", "-", "1rhi1c", "@|||@!", "a"};
        for (String arg : invalidArgs) {
            String expected = String.format(MESSAGE_EXPECTED_INVALID_INTEGER, arg);
            CommandParseException exception = assertThrows(
                    CommandParseException.class,
                    () -> new Parser().parseCommand(String.format("unmark %s", arg)));
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
        String[] invalidArgs = {" ", "   ", ""};
        String deleteInvalid = "Your delete command format is invalid..."
                + System.lineSeparator()
                + "Usage: delete <taskNumber>";
        for (String arg : invalidArgs) {
            String expected = String.format(deleteInvalid, arg);
            CommandParseException exception = assertThrows(
                    CommandParseException.class,
                    () -> new Parser().parseCommand(String.format("delete%s", arg)));
            assertEquals(expected, exception.getMessage());
        }
    }

    @Test
    public void parseCommand_invalidDeleteTaskNumber_exceptionThrown() {
        String[] invalidArgs = {"asd", "b", "-", "1rhi1c", "@|||@!"};
        for (String arg : invalidArgs) {
            String expected = String.format(MESSAGE_EXPECTED_INVALID_INTEGER, arg);
            CommandParseException exception = assertThrows(
                    CommandParseException.class,
                    () -> new Parser().parseCommand(String.format("delete %s", arg)));
            assertEquals(expected, exception.getMessage());
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
