package ru.nsu.shebanov.githubDSL.dsl;

import java.time.LocalDate;
import java.util.List;
import ru.nsu.shebanov.githubDSL.antlr.CourseDSLBaseVisitor;
import ru.nsu.shebanov.githubDSL.antlr.CourseDSLParser;

public class CourseVisitor extends CourseDSLBaseVisitor<Object> {

    public Course visitCourse(CourseDSLParser.CourseContext ctx) {
        List<Task> tasks = visitTasks(ctx.tasks());
        List<Student> students = visitStudents(ctx.students());
        List<Group> groups = visitGroups(ctx.groups());
        String downloadFolder = visitConfig(ctx.config());
        return new Course(tasks, students, groups, downloadFolder);
    }

    public List<Task> visitTasks(CourseDSLParser.TasksContext ctx) {
        return ctx.task().stream().map(this::visitTask).toList();
    }

    public Task visitTask(CourseDSLParser.TaskContext ctx) {
        int id = Integer.parseInt(ctx.INT(0).getText());
        String name = stripQuotes(ctx.STRING(0).getText());
        int maxPoints = Integer.parseInt(ctx.INT(1).getText());
        LocalDate softDeadline = LocalDate.parse(stripQuotes(ctx.STRING(1).getText()));
        LocalDate hardDeadline = LocalDate.parse(stripQuotes(ctx.STRING(2).getText()));
        return new Task(id, name, maxPoints, softDeadline, hardDeadline);
    }

    public List<Student> visitStudents(CourseDSLParser.StudentsContext ctx) {
        return ctx.student().stream()
                .map(this::visitStudent)
                .toList();
    }

    public Student visitStudent(CourseDSLParser.StudentContext ctx) {
        String nickname = stripQuotes(ctx.STRING(0).getText());
        String name = stripQuotes(ctx.STRING(1).getText());
        String repository = stripQuotes(ctx.STRING(2).getText());
        return new Student(nickname, name, repository);
    }

    public List<Group> visitGroups(CourseDSLParser.GroupsContext ctx) {
        return ctx.group().stream()
                .map(this::visitGroup)
                .toList();
    }

    public Group visitGroup(CourseDSLParser.GroupContext ctx) {
        String name = stripQuotes(ctx.STRING(0).getText());
        List<String> studentNicknames = ctx.STRING()
                .subList(1, ctx.STRING().size())
                .stream()
                .map(s -> stripQuotes(s.getText()))
                .toList();
        return new Group(name, studentNicknames);
    }

    public String visitConfig(CourseDSLParser.ConfigContext ctx) {
        return stripQuotes(ctx.STRING().getText());
    }

    private String stripQuotes(String s) {
        return s.substring(1, s.length() - 1);
    }
}
