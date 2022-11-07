package core.basesyntax;

import core.basesyntax.service.ReportWriterService;
import core.basesyntax.service.impl.ReportWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportWriterServiceTest {
    private static final List<String> REPORT =
            List.of("fruit,quantity", "banana,20", "apple,100", "orange,50");
    private static final String INVALID_PATH = "C:/Users/Bob/project/src/main/resources/file.csv";
    private static final String PATH_TO_TEST_REPORT_FILE = "src/test/resources/testReport.csv";
    private static ReportWriterService reportWriterService;

    @BeforeClass
    public static void beforeClass() {
        reportWriterService = new ReportWriterServiceImpl();
    }

    @Test
    public void saveReport_validData_ok() {
        reportWriterService.saveReport(REPORT, PATH_TO_TEST_REPORT_FILE);
        List<String> actualResult = readFromFile(PATH_TO_TEST_REPORT_FILE);
        Assert.assertEquals(REPORT, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void saveReport_toInvalidFile_exceptionExpected_ok() {
        reportWriterService.saveReport(REPORT, INVALID_PATH);
        Assert.assertTrue("You should throw a Runtime Exception", true);
    }

    @AfterClass
    public static void clearResults() throws Exception {
        Files.deleteIfExists(Path.of(PATH_TO_TEST_REPORT_FILE));
    }

    private List<String> readFromFile(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from  " + filePath, e);
        }
    }
}
