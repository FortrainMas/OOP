package ru.nsu.shebanov.githubDSL;
import org.junit.jupiter.api.Test;

public class DslTest {
    @Test
    void runMainWithDslFile() throws Exception {
        String[] args = {
                "D:\\Z\\Programming\\_Univeristy\\NSU\\OOP\\_OOP\\Task_2_4_1\\src\\main\\java\\ru\\nsu\\shebanov\\githubDSL\\config.dsl"
        };
        Main.main(args);
    }
}
