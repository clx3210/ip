package olivero.common;

public final class Responses {

    public static final String GREETING_MESSAGE = "Howdy-do! I'm Olivero, " +
            "What can I do for you?";
    public static final String RESPONSE_SAVE_FILE_NOT_FOUND = "Can't seem to " +
            "find a previous save file..";
    public static final String RESPONSE_SAVE_FILE_CORRUPT = "Oh no.. your " +
            "previous save file may have been corrupted..";

    public static final String RESPONSE_SAVE_FILE_FAILED = "Oh no.. " +
            "I can't seem to save your file..";

    public static final String RESPONSE_INVALID_NUMBER_FORMAT = "Did you pass " +
            "in a valid integer? Your input: ";

    public static final String RESPONSE_INVALID_DATE_FORMAT = """
            Oh... Seems like you formatted your date(s) wrongly?
            Correct date format: yyyy-mm-dd HHmm (e.g. 2019-10-15 1800)
            """;

    public static final String RESPONSE_UNKNOWN_COMMAND = "W-WHAT?! " +
            "I do not understand what you just said :(";

    public static final String RESPONSE_INVALID_DATE_ORDER = "/from date " +
            "CANNOT be AFTER /to date!!";
    public static final String RESPONSE_INVALID_TASK_NUMBER = "No task with " +
            "task number %d exists..";

}
