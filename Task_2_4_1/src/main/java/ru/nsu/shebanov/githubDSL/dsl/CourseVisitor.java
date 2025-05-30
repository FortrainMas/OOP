package ru.nsu.shebanov.githubDSL.dsl;

import java.time.LocalDate;
import java.util.List;
import ru.nsu.shebanov.githubDSL.antlr.CourseDSLBaseVisitor;
import ru.nsu.shebanov.githubDSL.antlr.CourseDSLParser;

/**
 * Antlr visitor entity.
 */
public class CourseVisitor extends CourseDSLBaseVisitor<Object> {

    /**
     * Constructor for everything.
     *
     * @param ctx course context
     * @return Course
     */
    public Course visitCourse(CourseDSLParser.CourseContext ctx) {
        List<Task> tasks = visitTasks(ctx.tasks());
        List<Student> students = visitStudents(ctx.students());
        List<Group> groups = visitGroups(ctx.groups());
        String downloadFolder = visitConfig(ctx.config());
        return new Course(tasks, students, groups, downloadFolder);
    }

    /**
     * Parse tasks.
     *
     * @param ctx task context
     * @return list of tasks
     */
    public List<Task> visitTasks(CourseDSLParser.TasksContext ctx) {
        return ctx.task().stream().map(this::visitTask).toList();
    }

    /**
     * Parse particular task.
     *
     * @param ctx task context
     * @return parsed task
     */
    public Task visitTask(CourseDSLParser.TaskContext ctx) {
        int id = Integer.parseInt(ctx.INT(0).getText());
        String name = stripQuotes(ctx.STRING(0).getText());
        int maxPoints = Integer.parseInt(ctx.INT(1).getText());
        LocalDate softDeadline = LocalDate.parse(stripQuotes(ctx.STRING(1).getText()));
        LocalDate hardDeadline = LocalDate.parse(stripQuotes(ctx.STRING(2).getText()));
        return new Task(id, name, maxPoints, softDeadline, hardDeadline);
    }

    /**
     * Parse students.
     *
     * @param ctx students context
     * @return list of students
     */
    public List<Student> visitStudents(CourseDSLParser.StudentsContext ctx) {
        return ctx.student().stream()
                .map(this::visitStudent)
                .toList();
    }

    /**
     * Parse students.
     *
     * @param ctx student context
     * @return Student
     */
    public Student visitStudent(CourseDSLParser.StudentContext ctx) {
        String nickname = stripQuotes(ctx.STRING(0).getText());
        String name = stripQuotes(ctx.STRING(1).getText());
        String repository = stripQuotes(ctx.STRING(2).getText());
        return new Student(nickname, name, repository);
    }

    /**
     * Parse groups.
     *
     * @param ctx groups context
     * @return list of groups
     */
    public List<Group> visitGroups(CourseDSLParser.GroupsContext ctx) {
        return ctx.group().stream()
                .map(this::visitGroup)
                .toList();
    }

    /**
     * Visit groups.
     *
     * @param ctx group context
     * @return Parsed group
     */
    public Group visitGroup(CourseDSLParser.GroupContext ctx) {
        String name = stripQuotes(ctx.STRING(0).getText());
        List<String> studentNicknames = ctx.STRING()
                .subList(1, ctx.STRING().size())
                .stream()
                .map(s -> stripQuotes(s.getText()))
                .toList();
        return new Group(name, studentNicknames);
    }

    /**
     * Visit config.
     *
     * @param ctx Config context
     * @return parse config
     */
    public String visitConfig(CourseDSLParser.ConfigContext ctx) {
        return stripQuotes(ctx.STRING().getText());
    }

    /**
     * Remove quotes.
     *
     * @param s string in quotes
     * @return string without them
     */
    private String stripQuotes(String s) {
        return s.substring(1, s.length() - 1);
    }
}
