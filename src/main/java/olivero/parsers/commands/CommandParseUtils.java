package olivero.parsers.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import olivero.common.Responses;
import olivero.exceptions.CommandParseException;

/**
 * Provides utility methods when parsing raw command user inputs.
 */
public class CommandParseUtils {



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

    public static List<Integer> parseIntegers(List<String> rawInts) throws CommandParseException {
        List<Integer> parsedIntegers = new ArrayList<>();
        for (String rawInt : rawInts) {
            parsedIntegers.add(parseInteger(rawInt));
        }
        return parsedIntegers;
    }

}
