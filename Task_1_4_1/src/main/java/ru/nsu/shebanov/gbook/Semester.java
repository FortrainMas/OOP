package ru.nsu.shebanov.gbook;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Semester {
    public Map<String, List<Integer>> marks = new HashMap<>();
    public Map<String, Integer> marksNumber = new HashMap<>();
    private final Set<String> allowedControlForms =
            new HashSet<>(Arrays.asList("задание", "контрольная", "коллоквиум",
                    "контрольная", "экзамен", "дифференцированный зачёт",
                    "зачёт", "защита отчёта по практике"));


    public Semester(Map<String, Integer> formsOfControl) {
        formsOfControl.keySet().forEach(x -> {
            if (allowedControlForms.contains(x)) {
                marks.put(x, new ArrayList<>());
                marksNumber.put(x, formsOfControl.get(x));
            } else {
                throw new IllegalArgumentException("Unsupported form of control");
            }
        });
    }


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

    public boolean noExamC() {
        AtomicBoolean res = new AtomicBoolean(true);

        marks.keySet().forEach(key -> {
            if (Objects.equals(key, "зачёт") ||
                    Objects.equals(key, "дифференцированный зачёт") ||
                    Objects.equals(key, "экзамен")) {
                marks.get(key).forEach(value -> {
                    if (value < 4) {
                        res.set(false);
                    }
                });
            }
        });

        return res.get();
    }
}
