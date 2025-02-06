package ru.nsu.shebanov.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ListTests {

    @Test
    void emptyList() {
        ListMarkdown.ListBuilder lb = new ListMarkdown.ListBuilder();

        String expected = "";
        String actual = lb.build().toString();

        assertEquals(expected, actual);
    }

    @Test
    void simpleList() {
        ListMarkdown.ListBuilder lb = new ListMarkdown.ListBuilder()
                .add(new Text.Plain("крутые поинты"))
                .add(new Text.Plain("крутые поинты"))
                .add(new Text.Plain("крутые поинты"));

        String expected = "-   крутые поинты\n"
                + "-   крутые поинты\n"
                + "-   крутые поинты";
        String actual = lb.build().toString();
        System.out.println(actual);
        assertEquals(expected, actual);
    }


    @Test
    void orderedList() {
        ListMarkdown.ListBuilder lb = new ListMarkdown.ListBuilder()
                .ordered()
                .add(new Text.Plain("крутые поинты"))
                .add(new Text.Plain("крутые поинты"))
                .add(new Text.Plain("крутые поинты"));

        String expected = "1.   крутые поинты\n"
                + "2.   крутые поинты\n"
                + "3.   крутые поинты";
        String actual = lb.build().toString();
        assertEquals(expected, actual);
    }

    @Test
    void unorderedList() {
        ListMarkdown.ListBuilder lb = new ListMarkdown.ListBuilder()
                .unordered()
                .add(new Text.Plain("крутые поинты"))
                .add(new Text.Plain("крутые поинты"))
                .add(new Text.Plain("крутые поинты"));

        String expected = "-   крутые поинты\n"
                + "-   крутые поинты\n"
                + "-   крутые поинты";
        String actual = lb.build().toString();
        assertEquals(expected, actual);
    }

    @Test
    void complexText() {
        ListMarkdown.ListElement le = new ListMarkdown.ListBuilder()
                .add("first")
                .add("second")
                .add("third")
                .ordered()
                .build();

        ListMarkdown.ListElement fin = new ListMarkdown.ListBuilder()
                .add("first")
                .add("second")
                .add(le)
                .build();

        String expected = "- first\n"
                + "- second\n"
                + "-   - first\n"
                + "    - second\n"
                + "    - third";

        assertEquals(expected, fin.toString());
    }
}
