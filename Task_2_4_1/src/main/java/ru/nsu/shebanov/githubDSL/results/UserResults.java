package ru.nsu.shebanov.githubDSL.results;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.shebanov.githubDSL.dsl.Course;

public class UserResults {
    public List<TaskResults> tr;
    public String userName;
    public Course course;

    public UserResults(Course course, String userName) {
        tr = new ArrayList<>();
        this.userName = userName;
        this.course = course;
    }

    public void appendEmpty() {
        for (var task : course.tasks) {
            boolean cont = false;
            for (var addedTask : tr) {
                cont = cont || addedTask.task_name.equals(task.name);
            }
            if (!cont) {
                tr.add(new TaskResults(task.name));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(this.userName);
        for (var task : tr) {
            res.append(task.toString());
        }
        return res.toString();
    }
}
