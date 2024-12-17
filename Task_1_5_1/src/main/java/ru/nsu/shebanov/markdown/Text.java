package ru.nsu.shebanov.markdown;

import java.util.Objects;

/**
 * Markdown for text.
 */
public class Text {
    /**
     * Plain text class.
     */
    public static class Plain extends Element {
        public String value;

        /**
         * Constructor.
         *
         * @param text text
         */
        public Plain(String text) {
            this.value = text;
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
            if (!(o instanceof Plain)) return false;
            Plain plain = (Plain) o;
            return Objects.equals(value, plain.value);
        }

        /**
         * Hashcode of text.
         *
         * @return hashcode
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }

        /**
         * Markdown representation.
         *
         * @return string
         */
        @Override
        public String toString() {
            return value;
        }
    }

    /**
     * Bold text.
     */
    public static class Bold extends Plain {
        /**
         * Constructor.
         *
         * @param text content
         */
        public Bold(String text) {
            super(text);
        }

        /**
         * Markdown representation.
         *
         * @return string
         */
        @Override
        public String toString() {
            return "**" + this.value + "**";
        }
    }

    /**
     * Italic text.
     */
    public static class Italic extends Plain {
        /**
         * Constructor.
         *
         * @param text content
         */
        public Italic(String text) {
            super(text);
        }

        /**
         * Markdown representation.
         *
         * @return string
         */
        @Override
        public String toString() {
            return "*" + this.value + "*";
        }
    }

    /**
     * Inline code text.
     */
    public static class InlineCode extends Plain {

        /**
         * Constructor.
         *
         * @param text content
         */
        public InlineCode(String text) {
            super(text);
        }

        /**
         * Markdown representation.
         *
         * @return string
         */
        @Override
        public String toString() {
            return "`" + this.value + "`";
        }
    }
}
