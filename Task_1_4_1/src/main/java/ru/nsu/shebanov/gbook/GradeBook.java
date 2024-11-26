package ru.nsu.shebanov.gbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GradeBook {
    private List<Subject> subjects;
    private int finalWork = -1;

    public GradeBook(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void setMark(String subjectName, int semester, String formOfControl, int mark) {
        this.subjects.forEach(subject->{
            if(Objects.equals(subject.subjectName, subjectName)) {
                subject.setMark(formOfControl, semester, mark);
            }
        });
    }

    public int averageMark() {
        int marksSum = this.subjects.stream()
                .map(subject -> subject.marksExtract().get(0))
                .reduce(0, Integer::sum);

        int marksNumber = this.subjects.stream()
                .map(subject -> subject.marksExtract().get(1))
                .reduce(0, Integer::sum);

        return marksSum / marksNumber;
    }

    public int determineLastSemester() {
        return this.subjects.stream()
                .map(subject -> subject.lastSemester)
                .reduce(-1, Math::max);
    }

    public boolean isAllowedToTransfer() {
        int lastSemester = determineLastSemester();

        return this.subjects.stream()
                .filter(subject -> subject.lastSemester == lastSemester)
                .map(Subject::lastSemesterNoC)
                .reduce(true, (t, subject) -> t && subject);
    }

    public boolean isHighScholarship() {
        int lastSemester = determineLastSemester();

        return this.subjects.stream()
                .filter(subject -> subject.lastSemester == lastSemester)
                .map(Subject::lastSemesterOnlyFives)
                .reduce(true, (t, subject) -> t && subject);
    }

    void setFinalWork(int mark){
        this.finalWork = mark;
    }

    public boolean diplomaHonors() {
        if(this.finalWork != -1 && this.finalWork < 5) {
            return false;
        }

        boolean noCForExams = this.subjects.stream()
                .map(Subject::allTimeNoCForExams)
                .reduce(true, (t, subject) -> t && subject);

        if(!noCForExams) {
            return false;
        }

        int totalDiplomaFivesMaximum = this.subjects.stream()
                .map(subject -> subject.finalMarkGoodAssumption() == 5 ? 1 : 0)
                .reduce(0, Integer::sum);

        return (double) totalDiplomaFivesMaximum / this.subjects.size() >= 0.75;
    }


}
