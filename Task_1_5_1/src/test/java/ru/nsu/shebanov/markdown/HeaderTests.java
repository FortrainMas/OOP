package ru.nsu.shebanov.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class HeaderTests {
    @Test
    void manyHeadersTest(){
        Header h1 = new Header(1, "Неуклюжий");
        Header h2 = new Header(2, "Буда");
        Header h3 = new Header(3, "На микро");
        Header h4 = new Header(4, "Давай");
        Header h5 = new Header(5, "Ченить");
        Header h6 = new Header(6, "Уроним");

        String expected = "# Неуклюжий" +
                "## Буда" +
                "### На микро" +
                "#### Давай" +
                "##### Ченить" +
                "###### Уроним";
        String actual = h1.toString() + h2 + h3
                + h4 + h5 + h6;

        assertEquals(expected, actual);
    }

    @Test
    void illegalArgument() {
        try {
            new Header(8, "pupupu");
            fail();
        } catch (IllegalArgumentException e){
        }
    }
}
