package olivero.common;

/**
 * Provides common display responses shown to the user.
 */
public final class Responses {

    /** Welcome message when the program starts. */
    public static final String GREETING_MESSAGE = "Howdy-do! I'm Olivero, "
            + "What can I do for you?";

    /** Display message when loading a save file has unknown error. */
    public static final String RESPONSE_SAVE_FILE_ERROR = "Unknown error when loading the save file..";

    /** Display message when a save file cannot be found. */
    public static final String RESPONSE_SAVE_FILE_NOT_FOUND = "Howdy-do! I'm Olivero, and it looks"
            + " like there isn't a previous save file."
            + System.lineSeparator()
            + "I'll try saving a new one the next time you add/modify a task!";

    /** Display message when a save file is corrupted. */
    public static final String RESPONSE_SAVE_FILE_CORRUPT = "Hey there! Your "
            + "previous save file may have been corrupted.. ";

    /** Display message when saving to disk failed. */
    public static final String RESPONSE_SAVE_FILE_FAILED = "Oh no.. "
            + "I can't seem to save your file..";

    /** Display message when an expected numerical input is invalid. */
    public static final String RESPONSE_INVALID_NUMBER_FORMAT = "Did you pass "
            + "in a valid integer? Your input: %s";

    /** Display message when an expected date input is invalid. */
    public static final String RESPONSE_INVALID_DATE_FORMAT = "Oh... "
            + "Seems like you formatted your date(s) wrongly?"
            + System.lineSeparator()
            + "Correct date format: yyyy-m-d Hmm (e.g. 2019-10-15 1800)";

    /** Display message when a user input does not match any supported commands. */
    public static final String RESPONSE_UNKNOWN_COMMAND = "W-WHAT?! "
            + "I do not understand what you just said :(";


    /** Display message when a later date happens before an earlier date. */
    public static final String RESPONSE_INVALID_DATE_ORDER = "/from date "
            + "CANNOT be AFTER /to date!!";

    /** Display message when using a non-existent task number of the task list */
    public static final String RESPONSE_INVALID_TASK_NUMBER = "No task with "
            + "task number %d exists..";

    public static final String RESPONSE_ILLEGAL_TASK_DESCRIPTION = "Task description should not contain "
            + "the following characters: %s";

    public static final String MESSAGE_DUPLICATE_TASK_NUMBER = "Duplicate task numbers are not allowed!";
    public static final String MESSAGE_INVALID_TASK_RANGE = "Invalid task number range specified!";
}
