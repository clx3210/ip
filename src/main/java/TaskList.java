import java.util.ArrayList;
import java.util.List;

public class TaskList {
  private final List<Task> tasks;
  public TaskList() {
    tasks = new ArrayList<>();
  }

  public void addTask(Task task) {
    tasks.add(task);
  }

  @Override
  public String toString() {
    StringBuilder message = new StringBuilder();
    for (int i = 1; i <= tasks.size(); i++) {
      message.append(i)
             .append(". ")
             .append(tasks.get(i - 1))
             .append("\n");
    }
    return message.toString();
  }
}
