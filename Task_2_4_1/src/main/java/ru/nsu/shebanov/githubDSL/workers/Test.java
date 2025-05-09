package ru.nsu.shebanov.githubDSL.workers;

import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

import java.io.File;
import java.io.IOException;

public class Test {
    private final String taskPath;

    public Test(String taskPath) {
        this.taskPath = taskPath;
    }

    public void run() throws IOException, InterruptedException {
        String command = String.format("cd '%s'; ./gradlew test jacocoTestReport", taskPath);
        ProcessBuilder builder = new ProcessBuilder("powershell", "-Command", command);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            System.out.println(taskPath + " failed (test or report error)");
            return;
        }

        File xmlFile = new File(taskPath + "/build/reports/jacoco/test/jacocoTestReport.xml");
        if (!xmlFile.exists()) {
            System.out.println(taskPath + " failed (no jacoco report)");
            return;
        }

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
            NodeList counters = doc.getElementsByTagName("counter");

            for (int i = 0; i < counters.getLength(); i++) {
                Element counter = (Element) counters.item(i);
                if ("INSTRUCTION".equals(counter.getAttribute("type"))) {
                    int covered = Integer.parseInt(counter.getAttribute("covered"));
                    int missed = Integer.parseInt(counter.getAttribute("missed"));
                    double coverage = 100.0 * covered / (covered + missed);

                    if (coverage >= 80.0) {
                        System.out.println(taskPath + " ok");
                    } else {
                        System.out.printf(taskPath + " failed (coverage %.2f%%)%n", coverage);
                    }
                    return;
                }
            }

            System.out.println(taskPath + " failed (no INSTRUCTION data)");
        } catch (Exception e) {
            System.out.println(taskPath + " failed (XML parse error)");
        }
    }
}
