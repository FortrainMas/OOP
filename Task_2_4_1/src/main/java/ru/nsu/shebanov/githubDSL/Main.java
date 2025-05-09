package ru.nsu.shebanov.githubDSL;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import ru.nsu.shebanov.githubDSL.antlr.CourseDSLLexer;
import ru.nsu.shebanov.githubDSL.antlr.CourseDSLParser;
import ru.nsu.shebanov.githubDSL.dsl.Course;
import ru.nsu.shebanov.githubDSL.dsl.CourseVisitor;

public class Main {
    public static void main(String[] args) throws Exception {
        CharStream input =
                CharStreams.fromFileName(
                        "D:\\Z\\Programming\\_Univeristy\\NSU\\OOP\\_OOP\\Task_2_4_1\\src\\main\\java\\ru\\nsu\\shebanov\\githubDSL\\config.dsl");
        CourseDSLLexer lexer = new CourseDSLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CourseDSLParser parser = new CourseDSLParser(tokens);

        ParseTree tree = parser.course();

        var visitor = new CourseVisitor();
        Course course = (Course) visitor.visit(tree);


        var tester = new Tester(course);
        tester.startTesting();
    }
}
