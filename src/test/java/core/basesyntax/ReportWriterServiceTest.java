package core.basesyntax;

import core.basesyntax.service.ReportWriterService;
import core.basesyntax.service.impl.ReportWriterServiceImpl;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportWriterServiceTest {
    private static final List<String> REPORT =
            List.of("fruit,quantity", "banana,20", "apple,100");
    private static final String INVALID_PATH = "C:/Users/Bob/project/src/main/resources/file.csv\"";
    private static final String PATH_TO_ORIGINAL_REPORT_FILE = "src/test/resources/report.csv";
    private static final String PATH_TO_TEST_REPORT_FILE = "src/test/resources/testReport.csv";
    private static ReportWriterService reportWriterService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportWriterService = new ReportWriterServiceImpl();
        Files.write(Path.of(PATH_TO_ORIGINAL_REPORT_FILE), ("fruit,quantity\n"
                + "banana,20\n" + "apple,100\n" + "orange,10\n").getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void saveReport_ok() {
        reportWriterService.saveReport(REPORT, PATH_TO_TEST_REPORT_FILE);
        List<String> actualResult = readFromFile(PATH_TO_TEST_REPORT_FILE);
        Assert.assertEquals(REPORT, actualResult);
    }

    @Test
    public void saveReport_toInvalidFile_exceptionExpected_Ok() {
        try {
            reportWriterService.saveReport(REPORT, INVALID_PATH);
        } catch (RuntimeException e) {
            Assert.assertTrue("You should throw a Runtime Exception", true);
        }
    }

    private List<String> readFromFile(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from  " + filePath, e);
        }
    }

    @After
    public void tearDown() throws Exception {
        try {
            Files.deleteIfExists(Path.of(PATH_TO_ORIGINAL_REPORT_FILE));
            Files.deleteIfExists(Path.of(PATH_TO_TEST_REPORT_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly clear result files after test ", e);
        }
    }
}
