package ru.nsu.shebanov.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CodeTests {
    @Test
    void codeTest() {
        String code = "package ru.nsu.shebanov.markdown;\n"
                + "\n"
                + "import static org.junit.jupiter.api.Assertions.assertEquals;\n"
                + "\n"
                + "import org.junit.jupiter.api.Test;\n"
                + "\n"
                + "class CodeTests {\n"
                + "    @Test\n"
                + "    void codeTest() {\n"
                + "        \n"
                + "    }\n"
                + "}";
        CodeBlock cd = new CodeBlock(code);

        String expected = "```\n"
                + "package ru.nsu.shebanov.markdown;\n"
                + "\n"
                + "import static org.junit.jupiter.api.Assertions.assertEquals;\n"
                + "\n"
                + "import org.junit.jupiter.api.Test;\n"
                + "\n"
                + "class CodeTests {\n"
                + "    @Test\n"
                + "    void codeTest() {\n"
                + "        \n"
                + "    }\n"
                + "}\n"
                + "```";
        String actual = cd.toString();
        assertEquals(expected, actual);
    }
}
