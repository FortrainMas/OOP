package ru.nsu.shebanov.githubDSL;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import ru.nsu.shebanov.githubDSL.antlr.CourseDSLLexer;
import ru.nsu.shebanov.githubDSL.antlr.CourseDSLParser;
import ru.nsu.shebanov.githubDSL.dsl.Course;
import ru.nsu.shebanov.githubDSL.dsl.CourseVisitor;
import ru.nsu.shebanov.githubDSL.results.Result;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Ошибка: укажи путь к файлу как аргумент командной строки.");
            System.exit(1);
        }

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
