package ru.nsu.shebanov.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class QuoteTests {
    @Test
    void simpleQuote() {
        String strContent = "Хорошо мне, садись - четыре";
        Quote q = new Quote(strContent);

        String expected = ">Хорошо мне, садись - четыре";

        String actual = q.toString();
        System.out.println(actual);

        assertEquals(expected, actual);
    }

    @Test
    void multilineQuote() {
        String strContent = "Мне долгое время было очень плохо в Вене.\n"
                + "Несколько месяцев я не ел горячей пищи. Питался молоком и чёрствым хлебом.\n"
                + "Но зато тратил 13 крейцеров в день "
                + "на сигареты. Выкуривал от 25 до 40 сигарет в день";
        Quote q = new Quote(strContent);

        String expected = ">Мне долгое время было очень плохо в Вене.\n"
                +">Несколько месяцев я не ел горячей пищи. Питался молоком и чёрствым хлебом.\n"
                + ">Но зато тратил 13 крейцеров в день "
                + "на сигареты. Выкуривал от 25 до 40 сигарет в день";
        String actual = q.toString();

        assertEquals(expected, actual);
    }

    @Test
    void complexQuote() {
        Quote q1 = new Quote("Blunt in my right hand");
        Quote q2 = new Quote("Cup in my left");
        Quote q2inner = new Quote(q2);
        Quote q3 = new Quote(q1, q2inner, "My gun on the night stand\nWhile");

        String expected = ">>Blunt in my right hand\n"
                + ">>>Cup in my left\n"
                + ">My gun on the night stand\n"
                + ">While";
        String actual = q3.toString();

        assertEquals(expected, actual);
    }
}
