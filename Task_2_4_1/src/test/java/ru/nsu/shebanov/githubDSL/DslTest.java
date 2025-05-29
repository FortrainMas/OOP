package ru.nsu.shebanov.githubDSL;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DslTest {
    @Test
    void runMainWithDslFile() throws Exception {
        URL resource = getClass().getClassLoader().getResource("config.dsl");
        if (resource == null) {
            throw new RuntimeException("DSL config not found in resources!");
        }

        Path absolutePath = Paths.get(resource.toURI());
        String[] args = { absolutePath.toString() };

        Main.main(args);
    }
}