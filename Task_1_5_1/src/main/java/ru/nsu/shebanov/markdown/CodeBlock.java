package ru.nsu.shebanov.markdown;

import java.util.Objects;

/**
 * Multiline code block for markdown.
 */
public class CodeBlock extends Element {
    private final String content;

    /**
     * Constructor for code block.
     *
     * @param content code
     */
    public CodeBlock(String content) {
        this.content = "```" + "\n" + content + "\n" + "```";
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
        if (!(o instanceof CodeBlock)) {
            return false;
        }
        CodeBlock codeBlock = (CodeBlock) o;
        return Objects.equals(content, codeBlock.content);
    }

    /**
     * Hashcode of code block.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(content);
    }

    /**
     * Markdown text.
     *
     * @return ```code```
     */
    @Override
    public String toString() {
        return this.content;
    }
}
