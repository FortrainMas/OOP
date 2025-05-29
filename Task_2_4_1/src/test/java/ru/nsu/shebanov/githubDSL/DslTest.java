package ru.nsu.shebanov.githubDSL;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class DslTest {
    @Test
    void runMainWithDslFile() throws Exception {
        Path relativePath = Paths.get("src/main/java/ru/nsu/shebanov/githubDSL/config.dsl");

        String absolutePath = relativePath.toAbsolutePath().normalize().toString();

        String[] args = { absolutePath };

        Main.main(args);
    }
}