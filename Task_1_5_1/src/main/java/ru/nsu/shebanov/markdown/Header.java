package ru.nsu.shebanov.markdown;

import java.io.Serializable;
import java.util.Objects;

public class Header extends Element {
    private final String content;
    private final int level;

    public Header (int level, String content) {
        if (level < 1 || level > 6) {
            throw new IllegalArgumentException("Header level must be between 1 and 6.");
        }

        this.content = "#".repeat(level) + " " + content;
        this.level = level;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Header)) return false;
        Header header = (Header) o;
        return level == header.level && Objects.equals(content, header.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, level);
    }

    @Override
    public String toString() {
        return this.content;
    }
}
