package ru.nsu.shebanov.markdown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Quote element for markdown.
 */
public class Quote extends Element {
    private final String content;

    /**
     * Produce quote from the couple of strings.
     *
     * @param elements strings
     */
    public Quote(String... elements) {
        List<String> stringElements = new ArrayList<>(Arrays.asList(elements));

        StringBuilder sb = new StringBuilder();

        for (var stringElement : stringElements) {
            List<String> lines = new ArrayList<>(
                    Arrays.asList(stringElement.split("\n")));
            for (var line : lines) {
                sb.append(">");
                sb.append(line);
                sb.append("\n");
            }
        }

        sb.deleteCharAt(sb.length() - 1);
        this.content = sb.toString();
    }

    /**
     * Build quote from other elements.
     *
     * @param elements string
     */
    public Quote(Object... elements) {
        this((new ArrayList<>(Arrays.asList(elements))).stream()
                .map(Object::toString).toArray(String[]::new));
    }

    /**
     * Test for equality.
     *
     * @param o object to compare with
     * @return true if equal, sometimes return false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quote)) {
            return false;
        }
        Quote quote = (Quote) o;
        return Objects.equals(content, quote.content);
    }


    /**
     * Hashcode for quote.
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(content);
    }

    /**
     * Markdown representation for quote.
     *
     * @return string for markdown
     */
    @Override
    public String toString() {
        return this.content;
    }
}
