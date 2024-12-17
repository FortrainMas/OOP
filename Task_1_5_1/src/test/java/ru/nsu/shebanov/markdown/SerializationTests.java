package ru.nsu.shebanov.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import java.io.*;

class SerializationTests{

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
        ){
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
    void TextTest() {
        Text.Italic serialized = new Text.Italic("text");
        checkSerialization(serialized, "text.txt");
    }

    @Test
    void CodeBlockTest() {
        CodeBlock serialized = new CodeBlock("CodeBlockTest();");
        checkSerialization(serialized, "text.txt");
    }

    @Test
    void ImageTest() {
        Image serialized = new Image("https://clck.ru/3FEhRP", "important image");
        checkSerialization(serialized, "text.txt");
    }

    @Test
    void QuoteTest() {
        Quote serialized = new Quote("Deutschland ueber ...");
        checkSerialization(serialized, "text.txt");
    }

    @Test
    void ListTests() {
        ListMD.ListElement le = new ListMD.ListBuilder()
                .add("first")
                .add("second")
                .add("third")
                .ordered()
                .build();

        ListMD.ListElement serialized = new ListMD.ListBuilder()
                .add(le)
                .add("enough")
                .build();

        checkSerialization(serialized, "text.txt");
    }

    @Test
    void TaskTests() {
        ListMD.ListElement le = new ListMD.ListBuilder()
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
    void TableTests() {
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

        Table.TableElement serialized = tableBuilder.build();

        checkSerialization(serialized, "test.txt");
    }

    @Test
    void PageTests() {
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

        ListMD.ListElement le = new ListMD.ListBuilder()
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
