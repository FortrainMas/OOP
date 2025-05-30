package ru.nsu.shebanov.githubDomainSpecificLanguage.results;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.shebanov.githubDomainSpecificLanguage.dsl.Course;

/**
 * User results.
 */
public class UserResults {
    public List<TaskResults> tr;
    public String userName;
    public Course course;

    /**
     * Constructor for user result.
     *
     * @param course course
     * @param userName user name
     */
    public UserResults(Course course, String userName) {
        tr = new ArrayList<>();
        this.userName = userName;
        this.course = course;
    }

    /**
     * Append missing tasks for user.
     */
    public void appendEmpty() {
        for (var task : course.tasks) {
            boolean cont = false;
            for (var addedTask : tr) {
                cont = cont || addedTask.taskName.equals(task.name);
            }
            if (!cont) {
                tr.add(new TaskResults(task.name));
            }
        }
    }

    /**
     * Construct string from results.
     *
     * @return simple markdown output
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(this.userName);
        for (var task : tr) {
            res.append(task.toString());
        }
        return res.toString();
    }
}
