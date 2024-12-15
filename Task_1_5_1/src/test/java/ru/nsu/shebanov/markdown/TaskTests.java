package ru.nsu.shebanov.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TaskTests {
    @Test
    void SimpleTasks () {
        Task.TaskBuilder tb = new Task.TaskBuilder();


        Task.TaskElement te =
                tb.add("ПРЕСС КАЧАТ", true)
                    .add("БЕГИТ", false)
                    .add("ТУРНИК", false)
                    .add("АНЖУМАНИЯ", false).build();

        String expected = "[x] ПРЕСС КАЧАТ\n" +
                "[ ] БЕГИТ\n" +
                "[ ] ТУРНИК\n" +
                "[ ] АНЖУМАНИЯ";

        assertEquals(expected, te.toString());
    }

    @Test
    void complextTasks() {
        Task.TaskBuilder tb = new Task.TaskBuilder();
        Header h1 = new Header(1, "Smoke");
        Header h2 = new Header(2, "Подойти к микрофону");
        Header h3 = new Header(3, "Записать все эти фразы");

        Task.TaskElement te = tb.add(h1, true)
                .add(h2, true)
                .add(h3, false)
                .build();

        String expected = "[x] # Smoke\n" +
                "[x] ## Подойти к микрофону\n" +
                "[ ] ### Записать все эти фразы";

        assertEquals(expected, te.toString());
    }
}
