package ru.nsu.shebanov.markdown;

import java.util.Objects;

/**
 * Class with markdown header.
 */
public class Header extends Element {
    private final String content;
    private final int level;

    /**
     * Constructor for header.
     *
     * @param level level of header
     * @param content content of header
     */
    public Header(int level, String content) {
        if (level < 1 || level > 6) {
            throw new IllegalArgumentException("Header level must be between 1 and 6.");
        }

        this.content = "#".repeat(level) + " " + content;
        this.level = level;
    }


    /**
     * Equality test for header.
     *
     * @param o object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Header)) return false;
        Header header = (Header) o;
        return level == header.level && Objects.equals(content, header.content);
    }

    /**
     * Hashcode of header.
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(content, level);
    }

    /**
     * Markdown string for header.
     *
     * @return #content
     */
    @Override
    public String toString() {
        return this.content;
    }
}
