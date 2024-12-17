package ru.nsu.shebanov.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.junit.jupiter.api.Test;

class SerializationTests {

    private <T> void checkSerialization(T serialize, String path) {
        try (
                FileOutputStream fos = new FileOutputStream(path);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(serialize);
        } catch (IOException e) {
            fail("Failed to open for serialization");
        }

        T deserialized = null;
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            try {
                deserialized = (T) ois.readObject();
            } catch (ClassNotFoundException e) {
                fail("Failed to read deserialized");
            }
        } catch (IOException e) {
            fail("Failed to open serialized");
        }

        if (deserialized == null) {
            fail("No deserialized object provided");
        }

        assertEquals(serialize, deserialized);
    }


    @Test
    void headersTest() {
        Header h1 = new Header(1, "Простой советский аптечный препарат...");
        checkSerialization(h1, "text.txt");
    }

    @Test
    void textTest() {
        Text.Italic serialized = new Text.Italic("text");
        checkSerialization(serialized, "text.txt");
    }

    @Test
    void codeBlockTest() {
        CodeBlock serialized = new CodeBlock("CodeBlockTest();");
        checkSerialization(serialized, "text.txt");
    }

    @Test
    void imageTest() {
        Image serialized = new Image("https://clck.ru/3FEhRP", "important image");
        checkSerialization(serialized, "text.txt");
    }

    @Test
    void quoteTest() {
        Quote serialized = new Quote("Deutschland ueber ...");
        checkSerialization(serialized, "text.txt");
    }

    @Test
    void listTests() {
        ListMarkdown.ListElement le = new ListMarkdown.ListBuilder()
                .add("first")
                .add("second")
                .add("third")
                .ordered()
                .build();

        ListMarkdown.ListElement serialized = new ListMarkdown.ListBuilder()
                .add(le)
                .add("enough")
                .build();

        checkSerialization(serialized, "text.txt");
    }

    @Test
    void taskTests() {
        ListMarkdown.ListElement le = new ListMarkdown.ListBuilder()
                .add("first")
                .add("second")
                .add("third")
                .ordered()
                .build();

        Task.TaskElement serialized = new Task.TaskBuilder()
                .add("do things", false)
                .add("do java", true)
                .add(le, true)
                .build();

        checkSerialization(serialized, "text.txt");
    }

    @Test
    void tableTests() {
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

        Table.TableElement serialized = tableBuilder.build();

        checkSerialization(serialized, "test.txt");
    }

    @Test
    void pageTests() {
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

        ListMarkdown.ListElement le = new ListMarkdown.ListBuilder()
                .add("first")
                .add("second")
                .add("third")
                .ordered()
                .build();

        Task.TaskElement te = new Task.TaskBuilder()
                .add("do things", false)
                .add("do java", true)
                .add(le, true)
                .build();

        Page.PageBuilder pb = new Page.PageBuilder();
        pb.add(tableBuilder.build());
        pb.add(te);
        pb.add(le);
        pb.add("Text");

        checkSerialization(pb.build(), "text.txt");

    }
}
