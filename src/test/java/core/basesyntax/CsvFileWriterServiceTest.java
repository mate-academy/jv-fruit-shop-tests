package core.basesyntax;

import static org.junit.Assert.assertTrue;

import core.basesyntax.serviceimpl.CsvFileWriterServiceImpl;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class CsvFileWriterServiceTest {
    private static CsvFileWriterServiceImpl csvFileWriterService;
    private static Map<String, String> reportLines;
    private static final String REPORT_TEST_FILE = "src/test/resources/report_test.csv";
    private static final String EXPECTED_REPORT_TEST_FILE
            = "src/test/resources/report_expected_test.csv";

    @Before
    public void setUp() {
        csvFileWriterService = new CsvFileWriterServiceImpl();
        reportLines = new HashMap<>();
        reportLines.put("banana","20");
        reportLines.put("apple","100");
    }

    @Test (expected = RuntimeException.class)
    public void csvFileWriter_FilePath_Empty() {
        csvFileWriterService.writeDataToFileCsv(reportLines,"");
    }

    @Test
    public void csvFileWriter_Write_OK() {
        csvFileWriterService.writeDataToFileCsv(reportLines,REPORT_TEST_FILE);
        File actualFile = new File(REPORT_TEST_FILE);
        File expectedFile = new File(EXPECTED_REPORT_TEST_FILE);
        try {
            assertTrue("The files differ!",
                    FileUtils.contentEquals(expectedFile, actualFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
