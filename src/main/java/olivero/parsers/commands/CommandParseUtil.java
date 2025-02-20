package olivero.parsers.commands;

import java.util.ArrayList;
import java.util.List;

import olivero.common.Responses;
import olivero.exceptions.CommandParseException;
import olivero.parsers.tasks.TaskParser;

/**
 * Provides utility methods when parsing raw command user inputs.
 */
public class CommandParseUtil {

    /**
     * Parses a string into an integer.
     *
     * @param rawInt The string representation of an integer.
     * @return The integer value of the parsed string, if successful.
     * @throws CommandParseException If the string cannot be parsed into an integer.
     */
    public static int parseInteger(String rawInt) throws CommandParseException {
        try {
            return Integer.parseInt(rawInt);
        } catch (NumberFormatException e) {
            throw new CommandParseException(
                    String.format(
                            Responses.RESPONSE_INVALID_NUMBER_FORMAT,
                            rawInt));
        }
    }

    /**
     * Parses a list of strings into a list of integers.
     *
     * @param integerStrings The list of strings to be parsed into integers.
     * @return List of integers if parsing is successful.
     * @throws CommandParseException If parsing fails.
     */
    public static List<Integer> parseIntegers(List<String> integerStrings) throws CommandParseException {
        List<Integer> parsedIntegers = new ArrayList<>();
        for (String rawInt : integerStrings) {
            parsedIntegers.add(parseInteger(rawInt));
        }
        return parsedIntegers;
    }

    /**
     * Validates a task description.
     *
     * @param description The task description to be validated.
     * @param emptyDescriptionResponse The response to show in the exception if the description is empty.
     * @throws CommandParseException If the task description is empty or contains reserved characters.
     */
    public static void validateTaskDescription(
            String description,
            String emptyDescriptionResponse) throws CommandParseException {
        if (description.isBlank()) {
            throw new CommandParseException(emptyDescriptionResponse);
        }
        if (description.contains(TaskParser.RESERVED_TOKEN)) {
            throw new CommandParseException(
                    String.format(Responses.RESPONSE_ILLEGAL_TASK_DESCRIPTION,
                            TaskParser.RESERVED_TOKEN));
        }
    }

}
