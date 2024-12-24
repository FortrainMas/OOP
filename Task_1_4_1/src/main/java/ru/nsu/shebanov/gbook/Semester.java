package ru.nsu.shebanov.gbook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Class for semester entity.
 */
public class Semester {
    public Map<String, List<Integer>> marks = new HashMap<>();
    public Map<String, Integer> marksNumber = new HashMap<>();
    private final Set<String> allowedControlForms =
            new HashSet<>(Arrays.asList("задание", "контрольная", "коллоквиум",
                    "контрольная", "экзамен", "дифференцированный зачёт",
                    "зачёт", "защита отчёта по практике"));


    /**
     * Initializes semester.
     *
     * @param formsOfControl set form of control in semester
     */
    public Semester(Map<String, Integer> formsOfControl) {
        formsOfControl.keySet().forEach(x -> {
            if (allowedControlForms.contains(x)) {
                marks.put(x, new ArrayList<>());
                marksNumber.put(x, formsOfControl.get(x));
            } else {
                throw new IllegalArgumentException("Unsupported form of control: " + x);
            }
        });
    }


    /**
     * Set mark in semester.
     *
     * @param formOfControl for which for or control
     * @param mark which mark
     */
    public void setMark(String formOfControl, int mark) {
        if (!marks.containsKey(formOfControl)) {
            throw new IllegalArgumentException(
                    "Form of control is not supported by such subject in such semester");
        }
        if (marks.get(formOfControl).size() == marksNumber.get(formOfControl)) {
            throw new IllegalArgumentException("All marks of such form of control are set");
        }

        marks.get(formOfControl).add(mark);
    }

    /**
     * No C for exams.
     *
     * @return is true or not
     */
    public boolean noExamC() {
        return marks.entrySet().stream()
                .filter(entry ->
                        Objects.equals(entry.getKey(), "зачёт") ||
                                Objects.equals(entry.getKey(), "дифференцированный зачёт") ||
                                Objects.equals(entry.getKey(), "экзамен")
                )
                .flatMap(entry -> entry.getValue().stream())
                .allMatch(value -> value >= 4);
    }
}
