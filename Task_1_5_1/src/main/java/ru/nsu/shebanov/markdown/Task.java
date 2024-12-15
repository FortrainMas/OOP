package ru.nsu.shebanov.markdown;

import java.util.ArrayList;
import java.util.List;

public class Task extends Element {
    public static class TaskElement extends Element {
        private final List<String> items;
        private final String content;

        public TaskElement(List<String> items) {
            this.items = items;
            this.content = String.join("\n", items);
        }

        @Override
        public String toString() {
            return this.content;
        }
    }

    public static class TaskBuilder {
        private final List<String> items = new ArrayList<>();

        public TaskBuilder add(String taskDescription, boolean isChecked) {
            String taskMarkdown = (isChecked ? "[x] " : "[ ] ") + taskDescription;
            items.add(taskMarkdown);
            return this;
        }

        public TaskBuilder add(Element taskDescription, boolean isChecked) {
            String taskMarkdown = (isChecked ? "[x] " : "[ ] ") + taskDescription;
            items.add(taskMarkdown);
            return this;
        }

        public TaskElement build() {
            return new TaskElement(items);
        }
    }
}
