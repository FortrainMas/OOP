package ru.nsu.shebanov.markdown;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class for handling markdown list builder.
 */
public class ListMarkdown {
    /**
     * Markdown list element.
     */
    public static class ListElement extends Element {
        private final List<String> items;

        /**
         * Constructor for element.
         *
         * @param items string of all needed elements
         */
        public ListElement(List<String> items) {
            this.items = items;
        }

        /**
         * Equality test.
         *
         * @param o object to compare with
         * @return idk
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ListElement)) {
                return false;
            }
            ListElement that = (ListElement) o;
            return Objects.equals(items, that.items);
        }

        /**
         * Hashcode of ... list.
         *
         * @return random(maybe not) number
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(items);
        }

        /**
         * String for markdown.
         *
         * @return markdown representation of list
         */
        @Override
        public String toString() {
            return String.join("\n", items);
        }
    }

    /**
     * Builder for markdown list.
     */
    public static class ListBuilder {
        private boolean isOrdered = false;
        private final List<String> items = new ArrayList<>();

        /**
         * Make list ordered.
         *
         * @return itself
         */
        public ListBuilder ordered() {
            this.isOrdered = true;
            return this;
        }

        /**
         * Make list unordered.
         *
         * @return itself
         */
        public ListBuilder unordered() {
            this.isOrdered = false;
            return this;
        }

        /**
         * add new element to the list.
         *
         * @param element string element
         * @return itself
         */
        public ListBuilder add(String element) {
            if (isOrdered) {
                items.add((items.size() + 1) + ". " + element);
            } else {
                items.add("- " + element);
            }
            return this;
        }

        /**
         * Add new element to the list.
         *
         * @param element element
         * @return itself
         */
        public ListBuilder add(Element element) {
            String itemMarkdown = element.toString().replace("\n", "\n    ");
            return add("  " + itemMarkdown);
        }

        /**
         * Build List element and returns it.
         *
         * @return list element
         */
        public ListElement build() {
            return new ListElement(items);
        }
    }
}
