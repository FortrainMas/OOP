package ru.nsu.shebanov.githubDSL;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import ru.nsu.shebanov.githubDSL.antlr.CourseDSLLexer;
import ru.nsu.shebanov.githubDSL.antlr.CourseDSLParser;
import ru.nsu.shebanov.githubDSL.dsl.Course;
import ru.nsu.shebanov.githubDSL.dsl.CourseVisitor;

/**
 * Enter point.
 */
public class Main {
    /**
     * Entry point function.
     *
     * @param args args
     * @throws Exception actually throws
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Ошибка: укажи путь к файлу как аргумент командной строки.");
            System.exit(1);
        }
        PrintStream utf8out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        System.setOut(utf8out);

        String filePath = args[0];
        CharStream input = CharStreams.fromFileName(filePath);

        CourseDSLLexer lexer = new CourseDSLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CourseDSLParser parser = new CourseDSLParser(tokens);

        ParseTree tree = parser.course();

        var visitor = new CourseVisitor();
        Course course = (Course) visitor.visit(tree);

        var tester = new Tester(course);
        tester.dryRun();
    }
}
