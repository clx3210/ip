package olivero.parsers.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.common.Responses;
import olivero.exceptions.CommandParseException;

/**
 * Represents a parser for parsing mass operations commands,
 * to be a part of a CommandParser class in a composition association.
 */
public class MassOpsParser {
    /** Specifies the maximum range of tasks can be affected in any consecutive maxOps operation */
    public static final int MAX_OPERATIONS_RANGE = 100;
    public static final String MESSAGE_TASK_RANGE_TOO_LARGE = "Task range is too large!";
    private static final Pattern MASS_OPS_COMMAND_FORMAT = Pattern.compile(
            " -m (?<taskNumbers>\\d+(?:\\s+\\d+)*)");

    private static final Pattern MASS_OPS_CONSECUTIVE_COMMAND_FORMAT = Pattern.compile(
            " -m (?<startTaskNumber>\\d+)-(?<endTaskNumber>\\d+)"
    );



    private final String invalidMassOpsParseMessage;
    private final String usageMessage;

    /**
     * Constructs a MassOpsParser object with the specified
     * invalid parse message and usage message.
     *
     * @param invalidParseMessage The invalid parse message to be shown when parsing fails initially.
     * @param usageMessage The usage message for a given command.
     */
    public MassOpsParser(String invalidParseMessage, String usageMessage) {
        this.invalidMassOpsParseMessage = invalidParseMessage;
        this.usageMessage = usageMessage;
    }

    private Set<Integer> parseMassConsecutiveTaskNumbers(Matcher matcher) throws CommandParseException {
        String startTaskNumberString = matcher.group("startTaskNumber").trim();
        String endTaskNumberString = matcher.group("endTaskNumber").trim();

        int startTaskNumber = CommandParseUtils.parseInteger(startTaskNumberString);
        int endTaskNumber = CommandParseUtils.parseInteger(endTaskNumberString);

        // Start task number cannot be greater than end task number.
        if (startTaskNumber > endTaskNumber) {
            throw new CommandParseException(
                    Responses.MESSAGE_INVALID_TASK_RANGE,
                    this.usageMessage
            );
        }
        // Range exceeds max tasks permissible.
        if ((long) endTaskNumber - (long) startTaskNumber + 1 > MAX_OPERATIONS_RANGE) {
            throw new CommandParseException(MESSAGE_TASK_RANGE_TOO_LARGE);
        }

        Set<Integer> taskNumbers = new HashSet<>();
        for (int i = startTaskNumber; i <= endTaskNumber; i++) {
            taskNumbers.add(i);
        }
        return taskNumbers;
    }

    private Set<Integer> parseMassTaskNumbers(Matcher matcher) throws CommandParseException {
        String taskNumbersString = matcher.group("taskNumbers").trim();
        List<Integer> taskNumberList = CommandParseUtils.parseIntegers(
                Arrays.asList(taskNumbersString.split("\\s+")));
        Set<Integer> taskNumbers = new HashSet<>(taskNumberList);

        // Throw error if task numbers contain duplicates.
        if (taskNumbers.size() != taskNumberList.size()) {
            throw new CommandParseException(
                    Responses.MESSAGE_DUPLICATE_TASK_NUMBER,
                    this.usageMessage
            );
        }

        return taskNumbers;
    }


    /**
     * Parses the arguments for mass operations commands.
     *
     * @param arguments The arguments to be parsed.
     * @return A set of task numbers to be affected by the mass operations.
     * @throws CommandParseException If parsing fails.
     */
    public Set<Integer> parse(String arguments) throws CommandParseException {
        final Matcher massMatcher = MASS_OPS_COMMAND_FORMAT.matcher(arguments);
        final Matcher massConsecutiveMatcher = MASS_OPS_CONSECUTIVE_COMMAND_FORMAT.matcher(arguments);
        final boolean isMassMatch = massMatcher.matches();
        final boolean isMassConsecutiveMatch = massConsecutiveMatcher.matches();

        if (!isMassMatch && !isMassConsecutiveMatch) {
            throw new CommandParseException(
                    this.invalidMassOpsParseMessage,
                    this.usageMessage
            );
        }
        // The mass operation matches should always be mutually exclusive.
        assert !isMassMatch || !isMassConsecutiveMatch;

        if (isMassMatch) {
            return parseMassTaskNumbers(massMatcher);
        } else {
            return parseMassConsecutiveTaskNumbers(massConsecutiveMatcher);
        }
    }


    /**
     * Returns true if arguments matches mass operations command format.
     *
     * @param arguments The argument to be matched.
     * @return True if arguments match mass operations command format; false otherwise.
     */
    public boolean isMassOpsMatch(String arguments) {
        final Matcher massMatcher = MASS_OPS_COMMAND_FORMAT.matcher(arguments);
        final Matcher massConsecutiveMatcher = MASS_OPS_CONSECUTIVE_COMMAND_FORMAT.matcher(arguments);
        return massMatcher.matches() || massConsecutiveMatcher.matches();
    }
}
