package ru.nsu.shebanov.markdown;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Page {
    public static class PageElement extends Element{
        public String content;

        public PageElement(String content) {
            this.content = content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PageElement)) return false;
            PageElement that = (PageElement) o;
            return Objects.equals(content, that.content);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(content);
        }

        @Override
        public String toString() {
            return this.content;
        }
    }

    public static class PageBuilder{
        List<String> stringElements;

        public PageBuilder() {
            this.stringElements = new ArrayList<>();
        }

        public void add(String element) {
            this.stringElements.add(element);
        }

        public void add(Element element) {
            this.stringElements.add(element.toString());
        }

        public void pop() {
            this.stringElements.remove(this.stringElements.size()-1);
        }

        public PageElement build() {
            return new PageElement(
                    String.join("\n\n", this.stringElements));
        }
    }
}
