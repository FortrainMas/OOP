package ru.nsu.shebanov.markdown;

public class Image extends Element {
    private final String src;
    private final String alt;

    public Image(String src, String alt) {
        if (src == null || src.trim().isEmpty()) {
            throw new IllegalArgumentException("Image source (src) cannot be empty.");
        }
        if (alt == null || alt.trim().isEmpty()) {
            throw new IllegalArgumentException("Image alternative text (alt) cannot be empty.");
        }

        this.src = src;
        this.alt = alt;
    }

    @Override
    public String toString() {
        return "![" + alt + "](" + src + ")";
    }
}
