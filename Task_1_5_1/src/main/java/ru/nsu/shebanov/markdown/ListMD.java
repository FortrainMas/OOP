package ru.nsu.shebanov.markdown;

import java.util.ArrayList;
import java.util.List;

public class ListMD {
    public static class ListElement extends Element {
        private final List<String> items;

        public ListElement(List<String> items) {
            this.items = items;
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
            String itemMarkdown = element.toString();
            return add(itemMarkdown);
        }

        public ListElement build() {
            return new ListElement(items);
        }
    }
}
