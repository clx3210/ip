package olivero.commands;

import olivero.exceptions.UnsupportedCommandException;

/**
 * Represents an executable command type.
 */
public enum CommandType {

    /** ToDo command type. */
    TODO("todo"),

    /** Deadline command type. */
    DEADLINE("deadline"),

    /** Event command type. */
    EVENT("event"),

    /** List command type. */
    LIST("list"),

    /** Mark command type. */
    MARK("mark"),

    /** Unmark command type. */
    UNMARK("unmark"),

    /** Delete command type. */
    DELETE("delete"),

    /** Exit command type. */
    BYE("bye"),

    /** Find command type. */
    FIND("find");

    private final String tag;
    CommandType(String tag) {
        this.tag = tag;
    }

    /**
     * Returns the enum constant of this type with the specified name,
     * where the provided string must exactly match the value specified in
     * some enum value's string value. Exception is thrown if no match is found.
     *
     * @param value The string value to match against that of existing enum values.
     * @return The unique Enum value associated with that string's value, if any.
     * @throws UnsupportedCommandException When {@code value} does not match any of the
     *                                     defined enum values.
     */
    public static CommandType asCommandType(String value) {
        try {
            return CommandType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedCommandException();
        }
    }

    /**
     * Returns the lower case value tag associated with the given
     * enum value.
     */
    @Override
    public String toString() {
        return this.tag;
    }
}
