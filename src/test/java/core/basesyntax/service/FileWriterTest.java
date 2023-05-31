package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterTest {
    private static final String REPORT_AFTER_DAY = "src/test/java/resources/ReportAfterDay.csv";
    private static final String INVALID_PATH = "";
    private static final String TEST_REPORT_FILE = "src/test/java/resources/TestReportFile.csv";
    private static final String EMPTY_FILE = "src/test/java/resources/EmptyFile.csv";
    private static final String SEPARATOR = System.lineSeparator();
    private static final String TITLE = "fruit,quantity";
    private static String reportAfterDay;
    private static FileWriter writer;

    @BeforeClass
    public static void beforeClass() {
        writer = new FileWriterImpl();
        StringBuilder stringBuilder = new StringBuilder();
        reportAfterDay = stringBuilder.append(TITLE)
                .append(SEPARATOR)
                .append("banana,40")
                .append(SEPARATOR)
                .append("apple,50")
                .toString();
    }

    @Test
    public void write_writeReport_Ok() {
        writer.write(reportAfterDay, REPORT_AFTER_DAY);
        List<String> expected = reader(TEST_REPORT_FILE);
        List<String> actual = reader(REPORT_AFTER_DAY);
        assertEquals(expected, actual);
    }

    @Test
    public void write_writeEmptyFile_Ok() {
        writer.write("", REPORT_AFTER_DAY);
        List<String> expected = reader(EMPTY_FILE);
        List<String> actual = reader(REPORT_AFTER_DAY);
        assertEquals(expected, actual);
    }

    @Test
    public void write_invalidPathToFile_NotOk() {
        try {
            writer.write(REPORT_AFTER_DAY, INVALID_PATH);
        } catch (Exception e) {
            return;
        }
        fail("Invalid path file: " + INVALID_PATH);
    }

    public List<String> reader(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + path, e);
        }
    }
}
