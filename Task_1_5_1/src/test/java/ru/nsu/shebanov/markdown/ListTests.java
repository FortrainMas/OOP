package ru.nsu.shebanov.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ListTests {

    @Test
    void emptyList() {
        ListMD.ListBuilder lb = new ListMD.ListBuilder();

        String expected = "";
        String actual = lb.build().toString();

        assertEquals(expected, actual);
    }

    @Test
    void simpleList() {
        ListMD.ListBuilder lb = new ListMD.ListBuilder()
                .add(new Text.Plain("крутые поинты"))
                .add(new Text.Plain("крутые поинты"))
                .add(new Text.Plain("крутые поинты"));

        String expected = "-   крутые поинты\n" +
                "-   крутые поинты\n" +
                "-   крутые поинты";
        String actual = lb.build().toString();
        System.out.println(actual);
        assertEquals(expected, actual);
    }


    @Test
    void orderedList() {
        ListMD.ListBuilder lb = new ListMD.ListBuilder()
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
        ListMD.ListBuilder lb = new ListMD.ListBuilder()
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
        ListMD.ListElement le = new ListMD.ListBuilder()
                .add("first")
                .add("second")
                .add("third")
                .ordered()
                .build();

        ListMD.ListElement fin = new ListMD.ListBuilder()
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
