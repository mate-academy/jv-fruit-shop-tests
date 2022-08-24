package core.basesyntax.io;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReportWriterTest {
    private ReportWriter reportWriter;
    private static String correctReport;
    private static final String CORRECT_PATH_TO_FILE = "src/test/resources/ReportFile.csv";

    @BeforeClass
    public static void beforeClass() {
        correctReport = new StringBuilder("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90")
                .toString();
    }

    @Before
    public void setUp() {
        reportWriter = new ReportWriterImpl();
    }

    @Test
    public void writeToFile_correctData_Ok() {
        reportWriter.writeToFile(correctReport, CORRECT_PATH_TO_FILE);
        List<String> expected = List.of("fruit,quantity","banana,152","apple,90");
        List<String> actual = readReport(CORRECT_PATH_TO_FILE);
        assertEquals(expected,actual);
    }

    @Test
    public void writeToFile_emptyReport_Ok() {
        String emptyReport = "";
        String pathToEmptyReport = "src/test/resources/EmptyReportFile.csv";
        reportWriter.writeToFile(emptyReport, pathToEmptyReport);
        List<String> expected = new ArrayList<>();
        List<String> actual = readReport(pathToEmptyReport);
        assertEquals(expected,actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_incorrectPathToReportFile_notOk() {
        String incorrectPath = "";
        reportWriter.writeToFile(correctReport, incorrectPath);
    }

    private List<String> readReport(String pathToFile) {
        List<String> linesFromFile;
        try {
            linesFromFile = Files.readAllLines(Path.of(pathToFile));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file - " + pathToFile, e);
        }
        return linesFromFile;
    }
}