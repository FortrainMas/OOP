package ru.nsu.shebanov.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TextTests {

    @Test
    void plainText() {
        Text.Plain tp = new Text.Plain("Ну типа просто текст");


        String expected = "Ну типа просто текст";
        String actual = tp.toString();

        assertEquals(expected, actual);
    }


    @Test
    void italicText() {
        Text.Italic tp = new Text.Italic("Текст на итальянском");


        String expected = "*Текст на итальянском*";
        String actual = tp.toString();

        assertEquals(expected, actual);
    }

    @Test
    void boldText() {
        Text.Bold tb = new Text.Bold("Лысый текст");

        String expected = "**Лысый текст**";
        String actual = tb.toString();

        assertEquals(expected, actual);
    }

    @Test
    void inlineCode() {
        Text.InlineCode ic = new Text.InlineCode("public static ya ustal");

        String expected = "`public static ya ustal`";
        String actual = ic.toString();

        assertEquals(expected, actual);
    }
}
