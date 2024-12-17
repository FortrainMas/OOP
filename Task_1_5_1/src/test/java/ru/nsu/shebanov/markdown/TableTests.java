package ru.nsu.shebanov.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class TableTests {
    @Test
    void simpleTest() {
        Table.TableBuilder tableBuilder = new Table.TableBuilder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .withRowLimit(8)
                .addRow("Index", "Random");
        for (int i = 1; i <= 5; i++) {
            if (i > 2) {
                tableBuilder.addRow(i, new
                        Text.Bold(String.valueOf(i * 3)));
            } else {
                tableBuilder.addRow(i, i * 3);
            }
        }

        String expected = "| Index | Random |\n"
                + "| ----: | :----- |\n"
                + "| 1     | 3      |\n"
                + "| 2     | 6      |\n"
                + "| 3     | **9**  |\n"
                + "| 4     | **12** |\n"
                + "| 5     | **15** |";
        String actual = tableBuilder.build().toString();

        assertEquals(expected, actual);
    }

    @Test
    void complexTest() {
        Table.TableBuilder tableBuilder = new Table.TableBuilder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .withRowLimit(8)
                .addRow("Index", "Random", "Empty for vibe");

        ListMD.ListElement le = new ListMD.ListBuilder()
                .add("first")
                .add("second")
                .add("third")
                .add("fourth")
                .ordered()
                .build();

        for (int i = 1; i <= 5; i++) {
            tableBuilder.addRow(le, String.valueOf(i));
        }

        Table.TableElement te = tableBuilder.build();

        String expected = "| Index    | Random | Empty for vibe |\n"
                + "| -------: | :----- | :------------- |\n"
                + "| - first<br>- second<br>- third<br>- fourth | 1      |                |\n"
                + "| - first<br>- second<br>- third<br>- fourth | 2      |                |\n"
                + "| - first<br>- second<br>- third<br>- fourth | 3      |                |\n"
                + "| - first<br>- second<br>- third<br>- fourth | 4      |                |\n"
                + "| - first<br>- second<br>- third<br>- fourth | 5      |                |";

        assertEquals(expected, te.toString());
    }

    @Test
    void limitTest() {
        Table.TableBuilder tableBuilder = new Table.TableBuilder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .withRowLimit(1)
                .addRow("Index", "Random", "Empty for vibe");

        try {
            tableBuilder.addRow("Text");
            fail();
        } catch (IllegalArgumentException e) {
            System.out.println("SLAVA BOGU");
        }
    }
}
