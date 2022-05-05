package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String REPORT_FILE = "src/test/resources/report.csv";
    private static final String EMPTY_FILE_PATH = "";
    private static FileWriterService fileWriter;
    private static String report;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterServiceImpl();
        report = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
    }

    @Test
    public void writeReportInvalidPath_ok() {
        fileWriter.write(REPORT_FILE, report);
        List<String> actual = getReportData(REPORT_FILE);
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,152");
        expected.add("apple,90");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeReportValidPath_notOk() {
        fileWriter.write(EMPTY_FILE_PATH, report);
    }

    private List<String> getReportData(String filePath) {
        List<String> reportData;
        try {
            reportData = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file by path: " + filePath, e);
        }
        return reportData;
    }
}
