package ru.nsu.shebanov.markdown;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Markdown task class.
 */
public class Task extends Element {
    /**
     * Built task element.
     */
    public static class TaskElement extends Element {
        private final List<String> items;
        private final String content;

        /**
         * List of items.
         *
         * @param items items
         */
        public TaskElement(List<String> items) {
            this.items = items;
            this.content = String.join("\n", items);
        }

        /**
         * Equality test.
         *
         * @param o object to compare with
         * @return true if equal, false, otherwise
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TaskElement)) return false;
            TaskElement that = (TaskElement) o;
            return Objects.equals(items, that.items) && Objects.equals(content, that.content);
        }

        /**
         * Hashcode of the task.
         *
         * @return maybe hashcode of the task
         */
        @Override
        public int hashCode() {
            return Objects.hash(items, content);
        }

        /**
         * Markdown representation of the string.
         *
         * @return string
         */
        @Override
        public String toString() {
            return this.content;
        }
    }

    /**
     * Builder for the task.
     */
    public static class TaskBuilder {
        private final List<String> items = new ArrayList<>();

        /**
         * Add new task.
         *
         * @param taskDescription content of the task.
         * @param isChecked is finished
         * @return itself
         */
        public TaskBuilder add(String taskDescription, boolean isChecked) {
            String taskMarkdown = (isChecked ? "- [x] " : "- [ ] ") + taskDescription;
            items.add(taskMarkdown);
            return this;
        }

        /**
         * Add new task.
         *
         * @param taskDescription content of the task
         * @param isChecked is finished
         * @return itself
         */
        public TaskBuilder add(Element taskDescription, boolean isChecked) {
            String taskMarkdown = (isChecked ? "- [x] " : "- [ ] ") + taskDescription;
            items.add(taskMarkdown);
            return this;
        }

        /**
         * Build the task.
         *
         * @return task element
         */
        public TaskElement build() {
            return new TaskElement(items);
        }
    }
}
