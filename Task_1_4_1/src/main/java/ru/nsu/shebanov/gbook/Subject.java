package ru.nsu.shebanov.gbook;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for subject.
 */
public class Subject {
    public String subjectName;
    public List<Semester> semesters;
    private final Map<Integer, Integer> semesterNumbers = new HashMap<>();
    public int lastSemester = -1;

    /**
     * Create subject based on its name, semesters, semester numbers.
     *
     * @param subjectName subject name
     * @param semesters semesters
     * @param semestersNumbers number of semesters
     */
    public Subject(String subjectName, List<Semester> semesters, List<Integer> semestersNumbers) {
        this.subjectName = subjectName;
        this.semesters = semesters;

        for (int i = 0; i < semestersNumbers.size(); i++) {
            this.semesterNumbers.put(semestersNumbers.get(i), i);
        }
    }

    /**
     * Set mark for subject.
     *
     * @param formOfControl form of control
     * @param semester semester
     * @param mark mark
     */
    void setMark(String formOfControl, int semester, int mark) {
        if (!semesterNumbers.containsKey(semester)) {
            throw new IllegalArgumentException("No such semester for such subject");
        }

        semesters.get(semesterNumbers.get(semester)).setMark(formOfControl, mark);
        lastSemester = semester;
    }

    /**
     * Extract marks and their number.
     *
     * @return array num of marks and their sum
     */
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
                                        semester.marks.get(key).stream()
                                                .reduce(0, Integer::sum))
                                .reduce(0, Integer::sum)
                )
                .reduce(0, Integer::sum);

        return Arrays.asList(setMarks, total);
    }

    /**
     * Check if there are only fives for last semester.
     *
     * @return true of false
     */
    public boolean lastSemesterOnlyFives() {
        int ls = semesterNumbers.get(lastSemester);

        return this.semesters.get(ls).marks.values().stream()
                .flatMap(List::stream)
                .allMatch(value -> value == 5);
    }


    /**
     * Last semester no C.
     *
     * @return true or false
     */
    public boolean lastSemesterNoC() {
        int ls = semesterNumbers.get(lastSemester);

        return this.semesters.get(ls).marks.values().stream()
                .flatMap(List::stream)
                .allMatch(value -> value >= 4);
    }

    /**
     * No C for exams on subject.
     *
     * @return true or false
     */
    public boolean allTimeNocForExams() {
        return this.semesters.stream()
                .allMatch(Semester::noExamC);
    }


    /**
     * final mark with an assumption of all fives.
     *
     * @return final mark
     */
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