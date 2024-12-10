package ru.nsu.shebanov.gbook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GradeBook {
    private List<Subject> subjects;
    private int finalWork = -1;

    /**
     * Constructor based on subjects.
     *
     * @param subjects subjects in grade book
     */
    public GradeBook(List<Subject> subjects) {
        this.subjects = subjects;
    }

    /**
     * Parses grade book and returns it.
     *
     * @param filePath path to the file with it
     * @return new grade book
     * @throws IOException fails if something wrong in reading
     */
    public static GradeBook parseGradeBook(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        List<Subject> subjects = new ArrayList<>();

        int subjectCount = Integer.parseInt(reader.readLine().trim());

        for (int i = 0; i < subjectCount; i++) {
            String[] subjectLine = reader.readLine().trim().split(" - ");
            String subjectName = subjectLine[0];
            int semesterCount = Integer.parseInt(subjectLine[1]);

            List<Semester> semesters = new ArrayList<>();
            List<Integer> semesterNumbers = new ArrayList<>();

            for (int j = 0; j < semesterCount; j++) {
                String[] nums = reader.readLine().trim().split(" ");
                int semesterNumber = Integer.parseInt(nums[0]);
                int forms = Integer.parseInt(nums[1]);
                semesterNumbers.add(semesterNumber);

                Map<String, Integer> controlForms = new HashMap<>();
                String line;
                while (forms != 0) {
                    forms -= 1;
                    line = reader.readLine();
                    String[] parts = line.split(" - ");
                    String formOfControl = parts[0].trim();
                    int count = Integer.parseInt(parts[1].trim());
                    controlForms.put(formOfControl, count);
                }

                semesters.add(new Semester(controlForms));

            }

            subjects.add(new Subject(subjectName, semesters, semesterNumbers));
        }

        reader.close();
        return new GradeBook(subjects);
    }

    /**
     * Set mark in grade book.
     *
     * @param subjectName subject
     * @param semester semester
     * @param formOfControl form of control
     * @param mark mark
     */
    public void setMark(String subjectName, int semester, String formOfControl, int mark) {
        this.subjects.forEach(subject -> {
            if (Objects.equals(subject.subjectName, subjectName)) {
                subject.setMark(formOfControl, semester, mark);
            }
        });
    }

    /**
     * Determine average mark for semester.
     *
     * @return average mark
     */
    public double averageMark() {
        int marksNumber = this.subjects.stream()
                .map(subject -> subject.marksExtract().get(0))
                .reduce(0, Integer::sum);

        int marksSum = this.subjects.stream()
                .map(subject -> subject.marksExtract().get(1))
                .reduce(0, Integer::sum);

        return (double) marksSum / marksNumber;
    }

    /**
     * Checks what semester was the last.
     *
     * @return number of semester
     */
    public int determineLastSemester() {
        return this.subjects.stream()
                .map(subject -> subject.lastSemester)
                .reduce(-1, Math::max);
    }

    /**
     * Is person allowed to transfer on budget.
     *
     * @return is it true or false
     */
    public boolean isAllowedToTransfer() {
        int lastSemester = determineLastSemester();

        return this.subjects.stream()
                .filter(subject -> subject.lastSemester == lastSemester)
                .map(Subject::lastSemesterNoC)
                .reduce(true, (t, subject) -> t && subject);
    }

    /**
     * Can high scholarship be acquired.
     *
     * @return is it true or false
     */
    public boolean isHighScholarship() {
        int lastSemester = determineLastSemester();

        return this.subjects.stream()
                .filter(subject -> subject.lastSemester == lastSemester)
                .map(Subject::lastSemesterOnlyFives)
                .reduce(true, (t, subject) -> t && subject);
    }

    /**
     * Set mark for final work.
     *
     * @param mark mark for it
     */
    void setFinalWork(int mark) {
        this.finalWork = mark;
    }

    /**
     * Sets if diploma with honors can be acquired.
     *
     * @return is it true or false
     */
    public boolean diplomaHonors() {
        if (this.finalWork != -1 && this.finalWork < 5) {
            return false;
        }

        System.out.println("CHUDO");
        boolean noCForExams = this.subjects.stream()
                .map(Subject::allTimeNoCForExams)
                .reduce(true, (t, subject) -> t && subject);

        System.out.println("A NE");
        if (!noCForExams) {
            return false;
        }

        int totalDiplomaFivesMaximum = this.subjects.stream()
                .map(subject -> subject.finalMarkGoodAssumption() == 5 ? 1 : 0)
                .reduce(0, Integer::sum);

        System.out.println("PREDMET");
        return (double) totalDiplomaFivesMaximum / this.subjects.size() >= 0.75;
    }


}
