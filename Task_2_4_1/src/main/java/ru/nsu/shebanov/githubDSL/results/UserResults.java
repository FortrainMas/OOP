package ru.nsu.shebanov.githubDSL.results;

import ru.nsu.shebanov.githubDSL.dsl.Course;

import java.util.ArrayList;
import java.util.List;

public class UserResults {
    public List<TaskResults> tr;
    public String userName;
    public Course course;

    public UserResults(Course course, String userName) {
        tr = new ArrayList<>();
        this.userName = userName;
    }

    public void appendEmpty() {
        for (var task : course.tasks) {
            boolean cont = false;
            for(var addedTask : tr) {
                cont = cont || addedTask.task_name.equals(task.name);
            }
            if(!cont) {
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
