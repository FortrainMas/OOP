package ru.nsu.shebanov.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PageTests {

    @Test
    void pageTest() {
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


        Page.PageBuilder pb = new Page.PageBuilder();
        pb.add(tableBuilder.build());
        pb.add(le);

        ListMarkdown.ListElement serialized = new ListMarkdown.ListBuilder()
                .add(le)
                .add("enough")
                .build();
        pb.add(serialized);
        pb.add("Text");


        String expected = "| Index | Random |\n"
                + "| ----: | :----- |\n"
                + "| 1     | 3      |\n"
                + "| 2     | 6      |\n"
                + "| 3     | **9**  |\n"
                + "| 4     | **12** |\n"
                + "| 5     | **15** |\n"
                + "\n"
                + "- first\n"
                + "- second\n"
                + "- third\n"
                + "\n"
                + "-   - first\n"
                + "    - second\n"
                + "    - third\n"
                + "- enough\n"
                + "\n"
                + "Text";
        assertEquals(expected, pb.build().toString());
    }

    @Test
    void complexTest() {
        Page.PageBuilder pb = new Page.PageBuilder();
        pb.add(new Header(1, "Demonstration"));

        Table.TableBuilder tableBuilder = new Table.TableBuilder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .withRowLimit(8)
                .addRow("Index", "Random", "Empty");
        for (int i = 1; i <= 5; i++) {
            if (i > 2) {
                tableBuilder.addRow(i, new
                        Text.Bold(String.valueOf(i * 3)));
            } else {
                tableBuilder.addRow(i, i * 3);
            }
        }

        ListMarkdown.ListElement inTableList = new ListMarkdown.ListBuilder()
                .add(new Text.Bold("bold"))
                .add(new Text.Plain("plain"))
                .add(new Text.InlineCode("inline"))
                .add(new Text.Italic("italic"))
                .build();

        tableBuilder.addRow(inTableList);
        tableBuilder.addRow();

        ListMarkdown.ListElement le = new ListMarkdown.ListBuilder()
                .ordered()
                .add("first")
                .add("second")
                .add("third")
                .build();

        pb.add(new Header(2, "Fancy table"));
        pb.add(tableBuilder.build());

        pb.add(new Header(2, "Lists"));
        pb.add(new Header(3, "Boring list"));
        pb.add(le);

        ListMarkdown.ListElement lst = new ListMarkdown.ListBuilder()
                .add(le)
                .add("enough")
                .build();
        pb.add(new Header(3, "Fancy list"));
        pb.add(lst);

        ListMarkdown.ListElement fancyList = new ListMarkdown.ListBuilder()
                .add(lst)
                .add(le)
                .add(new Text.Italic("Italic"))
                .build();
        pb.add(new Header(3, "Fanciest list"));
        pb.add(fancyList);


        String multilineCode = "ListMarkdown.ListElement lst = new ListMarkdown.ListBuilder()\n"
                + "                .add(le)\n"
                + "                .add(\"enough\")\n"
                + "                .build();\n"
                + "        pb.add(new Header(3, \"Fancy list\"));\n"
                + "        pb.add(lst);";
        pb.add(new Header(2, "Multiline code"));
        pb.add(new CodeBlock(multilineCode));

        Quote quote = new Quote("We shall fight in France, "
                + "we shall fight on the seas and oceans, "
                + "we shall fight on the beaches, "
                + "we shall fight on the landing grounds, "
                + "We shall never surrender.");
        pb.add(new Header(2, "Fancy quote"));
        pb.add(quote);

        String imageLink = "https://clck.ru/3FEhRP";
        String altText = "important image";

        Image image = new Image(imageLink, altText);
        pb.add(new Header(2, "Important image"));
        pb.add(image);

        Task.TaskElement task = new Task.TaskBuilder()
                .add(new Text.Bold("idk"), true)
                .add(new Text.Italic("idk"), false)
                .add("idk", false)
                .build();
        pb.add(new Header(2, "Tasks"));
        pb.add(task);
        System.out.print(pb.build());

        String expected = "# Demonstration\n"
                + "\n"
                + "## Fancy table\n"
                + "\n"
                + "| Index        | Random | Empty |\n"
                + "| -----------: | :----- | :---- |\n"
                + "| 1            | 3      |       |\n"
                + "| 2            | 6      |       |\n"
                + "| 3            | **9**  |       |\n"
                + "| 4            | **12** |       |\n"
                + "| 5            | **15** |       |\n"
                + "| -   **bold**<br>-   plain<br>-   `inline`<br>-   *italic* |        |       |\n"
                + "|              |        |       |\n"
                + "\n"
                + "## Lists\n"
                + "\n"
                + "### Boring list\n"
                + "\n"
                + "1. first\n"
                + "2. second\n"
                + "3. third\n"
                + "\n"
                + "### Fancy list\n"
                + "\n"
                + "-   1. first\n"
                + "    2. second\n"
                + "    3. third\n"
                + "- enough\n"
                + "\n"
                + "### Fanciest list\n"
                + "\n"
                + "-   -   1. first\n"
                + "        2. second\n"
                + "        3. third\n"
                + "    - enough\n"
                + "-   1. first\n"
                + "    2. second\n"
                + "    3. third\n"
                + "-   *Italic*\n"
                + "\n"
                + "## Multiline code\n"
                + "\n"
                + "```\n"
                + "ListMarkdown.ListElement lst = new ListMarkdown.ListBuilder()\n"
                + "                .add(le)\n"
                + "                .add(\"enough\")\n"
                + "                .build();\n"
                + "        pb.add(new Header(3, \"Fancy list\"));\n"
                + "        pb.add(lst);\n"
                + "```"
                + "\n"
                + "\n"
                + "## Fancy quote\n"
                + "\n"
                + ">We shall fight in France, "
                + "we shall fight on the seas and oceans, "
                + "we shall fight on the beaches, "
                + "we shall fight on the landing grounds, "
                + "We shall never surrender.\n"
                + "\n"
                + "## Important image\n"
                + "\n"
                + "![important image](https://clck.ru/3FEhRP)\n"
                + "\n"
                + "## Tasks\n"
                + "\n"
                + "- [x] **idk**\n"
                + "- [ ] *idk*\n"
                + "- [ ] idk";

        assertEquals(expected, pb.build().toString());
    }
}
