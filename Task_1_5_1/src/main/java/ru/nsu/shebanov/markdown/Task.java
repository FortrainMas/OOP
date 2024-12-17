package ru.nsu.shebanov.markdown;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Task extends Element {
    public static class TaskElement extends Element {
        private final List<String> items;
        private final String content;

        public TaskElement(List<String> items) {
            this.items = items;
            this.content = String.join("\n", items);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TaskElement)) return false;
            TaskElement that = (TaskElement) o;
            return Objects.equals(items, that.items) && Objects.equals(content, that.content);
        }

        @Override
        public int hashCode() {
            return Objects.hash(items, content);
        }

        @Override
        public String toString() {
            return this.content;
        }
    }

    public static class TaskBuilder {
        private final List<String> items = new ArrayList<>();

        public TaskBuilder add(String taskDescription, boolean isChecked) {
            String taskMarkdown = (isChecked ? "- [x] " : "- [ ] ") + taskDescription;
            items.add(taskMarkdown);
            return this;
        }

        public TaskBuilder add(Element taskDescription, boolean isChecked) {
            String taskMarkdown = (isChecked ? "- [x] " : "- [ ] ") + taskDescription;
            items.add(taskMarkdown);
            return this;
        }

        public TaskElement build() {
            return new TaskElement(items);
        }
    }
}
