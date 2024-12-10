package ru.nsu.shebanov.gbook;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Subject {
    public String subjectName;
    public List<Semester> semesters;
    private final Map<Integer, Integer> semesterNumbers = new HashMap<>();
    public int lastSemester = -1;

    public Subject(String subjectName, List<Semester> semesters, List<Integer> semestersNumbers) {
        this.subjectName = subjectName;
        this.semesters = semesters;

        for (int i = 0; i < semestersNumbers.size(); i++) {
            this.semesterNumbers.put(semestersNumbers.get(i), i);
        }
    }

    void setMark(String formOfControl, int semester, int mark) {
        if (!semesterNumbers.containsKey(semester)) {
            throw new IllegalArgumentException("No such semester for such subject");
        }

        semesters.get(semesterNumbers.get(semester)).setMark(formOfControl, mark);
        lastSemester = semester;
    }

    public List<Integer> marksExtract() {
        int setMarks = this.semesters.stream()
                .map(semester ->
                        semester.marks.keySet().stream()
                                .map(key -> semester.marks.get(key).size())
                                .reduce(0, Integer::sum)
                )
                .reduce(0, Integer::sum);

        int total = this.semesters.stream()
                .map(semester ->
                        semester.marks.keySet().stream()
                                .map(key ->
                                        semester.marks.get(key).stream().
                                                reduce(0, Integer::sum))
                                .reduce(0, Integer::sum)
                )
                .reduce(0, Integer::sum);

        return Arrays.asList(setMarks, total);
    }

    public boolean lastSemesterOnlyFives() {
        AtomicBoolean res = new AtomicBoolean(true);

        int ls = semesterNumbers.get(lastSemester);

        this.semesters.get(ls).marks.keySet().forEach(key ->
                this.semesters.get(ls).marks.get(key).forEach(value -> {
                    if (value < 5) {
                        res.set(false);
                    }
                }));

        return res.get();
    }

    public boolean lastSemesterNoC() {
        AtomicBoolean res = new AtomicBoolean(true);

        int ls = semesterNumbers.get(lastSemester);

        this.semesters.get(ls).marks.keySet().forEach(key -> {
            this.semesters.get(ls).marks.get(key).forEach(value -> {
                if (value < 4) {
                    res.set(false);
                }
            });
        });

        return res.get();
    }

    public boolean allTimeNoCForExams() {
        AtomicBoolean res = new AtomicBoolean(true);

        this.semesters.forEach(semester -> {
            if (!semester.noExamC()) {
                res.set(false);
            }
        });


        return res.get();
    }


    public int finalMarkGoodAssumption() {
        int semesterNumber = semesterNumbers.keySet().stream()
                .reduce(0, Math::max);
        if (lastSemester != semesterNumber) {
            return 5;
        } else {
            int ls = this.semesterNumbers.get(lastSemester);
            Semester sem = semesters.get(ls);
            if (!sem.marks.get("экзамен").isEmpty()) {
                return sem.marks.get("экзамен").get(0);
            } else if (!sem.marks.get("зачёт").isEmpty()) {
                return sem.marks.get("зачёт").get(0);
            } else {
                if (!sem.marks.get("дифференцированный зачёт").isEmpty()) {
                    return sem.marks.get("дифференцированный зачёт").get(0);
                }
                return 5;
            }
        }
    }
}