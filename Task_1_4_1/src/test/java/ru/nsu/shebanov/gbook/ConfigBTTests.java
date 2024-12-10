package ru.nsu.shebanov.gbook;

import org.junit.jupiter.api.Test;

import java.io.Console;
import java.io.IOException;

public class ConfigBTTests {
    @Test
    void basicTest(){
        try {
            GradeBook gb = GradeBook.parseGradeBook("C://Users/Ivans/Desktop/testconf.txt");
            System.out.println(gb);
        } catch (Exception e) {
            System.out.println("Failed");
        }
    }
}
