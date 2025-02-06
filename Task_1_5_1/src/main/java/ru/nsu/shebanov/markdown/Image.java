package ru.nsu.shebanov.markdown;

import java.util.Objects;

/**
 * Image object for markdown.
 */
public class Image extends Element {
    private final String src;
    private final String alt;

    /**
     * Constructor of image.
     *
     * @param src source of image
     * @param alt alternative text
     */
    public Image(String src, String alt) {
        this.src = src;
        this.alt = alt;
    }

    /**
     * Test for equality.
     *
     * @param o object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Image)) {
            return false;
        }
        Image image = (Image) o;
        return Objects.equals(src, image.src) && Objects.equals(alt, image.alt);
    }

    /**
     * Hash code.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(src, alt);
    }

    /**
     * Markdown text.
     *
     * @return ![alt](src)
     */
    @Override
    public String toString() {
        return "![" + alt + "](" + src + ")";
    }
}
