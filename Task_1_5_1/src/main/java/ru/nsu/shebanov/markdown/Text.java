package ru.nsu.shebanov.markdown;

import java.util.Objects;

public class Text {
    public static class Plain extends Element {
        public String value;

        public Plain(String text) {
            this.value = text;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Plain)) return false;
            Plain plain = (Plain) o;
            return Objects.equals(value, plain.value);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public static class Bold extends Plain {
        public Bold(String text) {
            super(text);
        }

        @Override
        public String toString() {
            return "**" + this.value + "**";
        }
    }

    public static class Italic extends Plain {
        public Italic(String text) {
            super(text);
        }

        @Override
        public String toString() {
            return "*" + this.value + "*";
        }
    }

    public static class InlineCode extends Plain {
        public InlineCode(String text) {
            super(text);
        }

        @Override
        public String toString() {
            return "`" + this.value + "`";
        }
    }
}
