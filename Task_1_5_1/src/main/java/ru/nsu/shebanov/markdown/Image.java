package ru.nsu.shebanov.markdown;

import java.util.Objects;

public class Image extends Element {
    private final String src;
    private final String alt;

    public Image(String src, String alt) {
        this.src = src;
        this.alt = alt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image)) return false;
        Image image = (Image) o;
        return Objects.equals(src, image.src) && Objects.equals(alt, image.alt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(src, alt);
    }

    @Override
    public String toString() {
        return "![" + alt + "](" + src + ")";
    }
}
