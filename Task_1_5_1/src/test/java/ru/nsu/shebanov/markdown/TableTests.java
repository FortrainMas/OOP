package ru.nsu.shebanov.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TableTests {
    @Test
    void simpleTest() {
        Table.TableBuilder tableBuilder = new Table.TableBuilder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .withRowLimit(8)
                .addRow("Index", "Random");
        for (int i = 1; i <= 5; i ++) {
            if (i > 2) {
                tableBuilder.addRow(i, new
                        Text.Bold(String.valueOf(i*3)));
            } else {
                tableBuilder.addRow(i, i*3);
            }
        }

        String expected = "| Index | Random |\n" +
                "| ----: | :----- |\n" +
                "| 1     | 3      |\n" +
                "| 2     | 6      |\n" +
                "| 3     | **9**  |\n" +
                "| 4     | **12** |\n" +
                "| 5     | **15** |";
        String actual = tableBuilder.build().toString();

        assertEquals(expected, actual);
    }
}
