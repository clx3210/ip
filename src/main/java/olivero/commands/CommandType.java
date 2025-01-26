package olivero.commands;

import olivero.exceptions.UnsupportedCommandException;

public enum CommandType {
    TODO("todo"), DEADLINE("deadline"), EVENT("event"),
    LIST("list"), MARK("mark"), UNMARK("unmark"), DELETE("delete"),
    BYE("bye"), FIND("find");
    private final String tag;
    CommandType(String tag) {
        this.tag = tag;
    }

    /**
     * @param value
     * @return the unique Enum value associated with that string's value, if any.
     * @throws UnsupportedCommandException when s does not match any of the
     * defined enum values.
     */
    public static CommandType asCommandType(String value) {
        try {
            return CommandType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedCommandException();
        }
    }

    @Override
    public String toString() {
        return this.tag;
    }
}
