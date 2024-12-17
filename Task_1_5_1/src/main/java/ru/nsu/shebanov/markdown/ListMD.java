package ru.nsu.shebanov.markdown;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListMD {
    public static class ListElement extends Element {
        private final List<String> items;

        public ListElement(List<String> items) {
            this.items = items;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ListElement)) return false;
            ListElement that = (ListElement) o;
            return Objects.equals(items, that.items);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(items);
        }

        @Override
        public String toString() {
            return String.join("\n", items);
        }
    }

    public static class ListBuilder {
        private boolean isOrdered = false;
        private final List<String> items = new ArrayList<>();

        public ListBuilder ordered() {
            this.isOrdered = true;
            return this;
        }

        public ListBuilder unordered() {
            this.isOrdered = false;
            return this;
        }

        public ListBuilder add(String element) {
            if (isOrdered) {
                items.add((items.size() + 1) + ". " + element);
            } else {
                items.add("- " + element);
            }
            return this;
        }

        public ListBuilder add(Element element) {
            String itemMarkdown = element.toString().replace("\n", "\n    ");
            return add("  " + itemMarkdown);
        }

        public ListElement build() {
            return new ListElement(items);
        }
    }
}
