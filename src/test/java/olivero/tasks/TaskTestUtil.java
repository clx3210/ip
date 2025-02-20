package olivero.tasks;

/**
 * Provides utility methods for task unit tests.
 */
public final class TaskTestUtil {
    public static String getExpectedTaskListString() {
        return String.join(System.lineSeparator(),
                "1. [T][X] Todo 1",
                "2. [T][ ] Todo 2",
                "3. [T][X] Todo 3",
                "4. [E][X] Event 1 (from: Jan 25 2028 2323 to: Feb 25 2028 2323)",
                "5. [E][ ] Event 2 (from: Jan 25 2028 2323 to: Feb 25 2028 2323)",
                "6. [E][X] Event 3 (from: Jan 25 2028 2323 to: Feb 25 2028 2323)",
                "7. [E][ ] Event 4 (from: Jan 25 2028 2323 to: Feb 25 2028 2323)",
                "8. [E][X] Event 5 (from: Jan 25 2028 2323 to: Feb 25 2028 2323)",
                "9. [D][ ] Deadline 1 (by: Jan 25 2028 2323)",
                "10. [D][X] Deadline 2 (by: Jan 25 2028 2323)",
                "11. [D][ ] Deadline 3 (by: Jan 25 2028 2323)",
                "12. [D][X] Deadline 4 (by: Jan 25 2028 2323)",
                "13. [D][ ] Deadline 5 (by: Jan 25 2028 2323)",
                "14. [D][X] Deadline 6 (by: Jan 25 2028 2323)");
    }

    public static String getExpectedFormattedString() {
        return String.join(System.lineSeparator(),
                "T | 0 | Todo 1",
                "T | 1 | Todo 2",
                "T | 0 | Todo 3",
                "E | 0 | Event 1 | 2028-1-25 2323 | 2028-2-25 2323",
                "E | 1 | Event 2 | 2028-1-25 2323 | 2028-2-25 2323",
                "E | 0 | Event 3 | 2028-1-25 2323 | 2028-2-25 2323",
                "E | 1 | Event 4 | 2028-1-25 2323 | 2028-2-25 2323",
                "E | 0 | Event 5 | 2028-1-25 2323 | 2028-2-25 2323",
                "D | 0 | Deadline 1 | 2028-1-25 2323",
                "D | 1 | Deadline 2 | 2028-1-25 2323");
    }
}
