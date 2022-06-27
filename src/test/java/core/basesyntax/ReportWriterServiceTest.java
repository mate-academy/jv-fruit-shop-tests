package core.basesyntax;

import core.basesyntax.service.impl.ReportWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportWriterServiceTest {
    private static ReportWriterServiceImpl reportWriterService;

    @BeforeClass
    public static void initialSetup() {
        reportWriterService = new ReportWriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void reportFile_wrongPath_notOk() {
        String wrongPath = "src/test/wrong/test-report-expected.csv";
        reportWriterService.writeReport(new ArrayList<>(), wrongPath);
    }

    @Test
    public void writeReport_validPath_ok() {
        List<String> report = new ArrayList<>();
        report.add("fruit,quantity");
        report.add(System.lineSeparator() + "banana,152");
        report.add(System.lineSeparator() + "apple,90");
        String resultPath = "src/test/test-resources/test-report-result.csv";
        String expectedPath = "src/test/test-resources/test-report-expected.csv";
        reportWriterService.writeReport(report, resultPath);
        List<String> result = readFile(resultPath);
        List<String> expected = readFile(expectedPath);
        Assert.assertEquals(expected, result);
    }

    private List<String> readFile(String path) {
        List<String> result;
        try {
            result = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + path, e);
        }
        return result;
    }
}
