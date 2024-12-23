package ru.nsu.shebanov.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ImageTests {
    @Test
    void imageTest() {
        String imageLink = "https://clck.ru/3FEhRP";
        String altText = "important image";

        Image image = new Image(imageLink, altText);

        String expected = "![important image](https://clck.ru/3FEhRP)";
        String actual = image.toString();

        assertEquals(expected, actual);
    }
}
