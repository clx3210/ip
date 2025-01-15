import java.util.ArrayList;
import java.util.List;

public class TaskList {
  private final List<String> texts;
  public TaskList() {
    texts = new ArrayList<>();
  }

  public void addTask(String task) {
    texts.add(task);
  }

  @Override
  public String toString() {
    StringBuilder message = new StringBuilder();
    for (int i = 1; i <= texts.size(); i++) {
      message.append(i)
             .append(". ")
             .append(texts.get(i - 1))
             .append("\n");
    }
    return message.toString();
  }
}
