package ru.nsu.shebanov.markdown;

public class CodeBlock extends Element{
    private final String content;

    public CodeBlock(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Code block content cannot be empty.");
        }

        this.content = "```" + "\n" + content + "\n" + "```";
    }

    @Override
    public String toString() {
        return this.content;
    }
}
