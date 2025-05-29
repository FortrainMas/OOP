package ru.nsu.shebanov.githubDSL;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DslTest {
    @Test
    void runMainWithDslFile() throws Exception {
        Path relativePath = Paths.get("src/main/java/ru/nsu/shebanov/githubDSL/config.dsl");

        Path absolutePath = relativePath.toAbsolutePath().normalize();

        if (!Files.exists(absolutePath)) {
            throw new RuntimeException("🔥 DSL config not found at: " + absolutePath);
        }

        String[] args = { absolutePath.toString() };

        Main.main(args);
    }
}