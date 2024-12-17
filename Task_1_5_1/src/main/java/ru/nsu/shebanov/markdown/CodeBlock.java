package ru.nsu.shebanov.markdown;

import java.util.Objects;

public class CodeBlock extends Element{
    private final String content;

    public CodeBlock(String content) {
        this.content = "```" + "\n" + content + "\n" + "```";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodeBlock)) return false;
        CodeBlock codeBlock = (CodeBlock) o;
        return Objects.equals(content, codeBlock.content);
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
