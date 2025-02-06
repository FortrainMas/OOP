package ru.nsu.shebanov.markdown;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Page for markdown.
 */
public class Page {
    /**
     * Built page element.
     */
    public static class PageElement extends Element {
        public String content;

        /**
         * Page element constructor.
         *
         * @param content content of the page
         */
        public PageElement(String content) {
            this.content = content;
        }

        /**
         * Why I write it.
         *
         * @param o object to compare with
         * @return true if something, false if something
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof PageElement)) {
                return false;
            }
            PageElement that = (PageElement) o;
            return Objects.equals(content, that.content);
        }

        /**
         * Hash code of page.
         *
         * @return hash code of kyzyl
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(content);
        }

        /**
         * Markdown for page.
         *
         * @return markdown for page
         */
        @Override
        public String toString() {
            return this.content;
        }
    }


    /**
     * Builder for page.
     */
    public static class PageBuilder {
        List<String> stringElements;

        /**
         * Constructor for page builder.
         */
        public PageBuilder() {
            this.stringElements = new ArrayList<>();
        }

        /**
         * Add new string element.
         *
         * @param element string element
         */
        public void add(String element) {
            this.stringElements.add(element);
        }

        /**
         * Add new element.
         *
         * @param element not string element
         */
        public void add(Element element) {
            this.stringElements.add(element.toString());
        }

        /**
         * Build and produce page.
         *
         * @return markdown of page
         */
        public PageElement build() {
            return new PageElement(
                    String.join("\n\n", this.stringElements));
        }
    }
}
