package core.basesyntax.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String TEST_REPORT_PATH =
            "src/test/java/resources/test_expectedReport.csv";
    private static final String REPORT_PATH = "src/test/java/resources/test_report.csv";
    private static final String EXPECTED_REPORT = "fruit,quantity"
            + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();
    private static FileWriter fileWriter;
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileWriter = new FileWriterImpl();
        fileReader = new FileReaderImpl();
    }

    @Test
    public void createReport_CorrectWriter_Ok() {
        fileWriter.createReport(EXPECTED_REPORT, TEST_REPORT_PATH);
        List<String> expected;
        try {
            expected = Files.readAllLines(Path.of(REPORT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("We can't read from file", e);
        }
        List<String> actual = fileReader.readFromFile(TEST_REPORT_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void createReport_TestWithIncorrectPath_NotOk() {
        fileWriter.createReport(EXPECTED_REPORT, "");
    }
}
