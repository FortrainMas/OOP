package ru.nsu.shebanov.githubDSL.workers;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ru.nsu.shebanov.githubDSL.results.TaskResults;

public class Test {
    private final String taskPath;
    public TaskResults tr;
    private final int timeout;

    public Test(String taskPath, TaskResults tr) {
        this.taskPath = taskPath;
        this.tr = tr;

        this.timeout = 70;
    }

    public void run() throws IOException, InterruptedException {
        String command = String.format("cd '%s'; ./gradlew test jacocoTestReport", taskPath);
        ProcessBuilder builder = new ProcessBuilder("powershell", "-Command", command);
        builder.redirectErrorStream(true);
        Process process = builder.start();

        boolean isFinished = process.waitFor(this.timeout, TimeUnit.SECONDS);
        if (!isFinished) {
            System.out.println(taskPath + " failed test (timeout)");
            return;
        }

        int exitCode = process.exitValue();

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
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature(
                    "http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            factory.setFeature("http://xml.org/sax/features/validation", false);
            factory.setNamespaceAware(false);

            DocumentBuilder builderXML = factory.newDocumentBuilder();
            Document doc = builderXML.parse(xmlFile);
            NodeList counters = doc.getElementsByTagName("counter");

            for (int i = 0; i < counters.getLength(); i++) {
                Element counter = (Element) counters.item(i);
                if ("INSTRUCTION".equals(counter.getAttribute("type"))) {
                    int covered = Integer.parseInt(counter.getAttribute("covered"));
                    int missed = Integer.parseInt(counter.getAttribute("missed"));
                    double coverage = 100.0 * covered / (covered + missed);

                    tr.testScore = coverage;
                    if (coverage >= 80.0) {
                        tr.testResults = true;
                        System.out.printf(taskPath + " ok (coverage %.2f%%)%n", coverage);
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
