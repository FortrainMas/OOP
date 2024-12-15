package ru.nsu.shebanov.markdown;

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
    public String toString() {
        return this.content;
    }
}
