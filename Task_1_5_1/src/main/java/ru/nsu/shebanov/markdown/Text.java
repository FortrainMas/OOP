package ru.nsu.shebanov.markdown;

public class Text {
    public static class Plain extends Element {
        public String value;

        public Plain(String text) {
            this.value = text;
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
