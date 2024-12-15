package ru.nsu.shebanov.markdown;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Quote extends Element {
    private final String content;

    public Quote(String... elements) {
        List<String> sElements = new ArrayList<>(Arrays.asList(elements));

        StringBuilder sb = new StringBuilder();

        for(var sElement : sElements) {
            List<String> lines = new ArrayList<>(Arrays.asList(sElement.split("\n")));
            for(var line : lines) {
                sb.append(">");
                sb.append(line);
                sb.append("\n");
            }
        }

        sb.deleteCharAt(sb.length() - 1);
        this.content = sb.toString();
    }

    public Quote(Object...  elements) {
        this((new ArrayList<>(Arrays.asList(elements))).stream()
                .map(Object::toString).toArray(String[]::new));
    }

    @Override
    public String toString() {
        return this.content;
    }
}
