package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.serviceimpl.CsvFileWriterServiceImpl;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterServiceTest {
    private static CsvFileWriterServiceImpl csvFileWriterService;
    private static Map<String, String> reportLines;
    private static final String REPORT_TEST_FILE = "src/test/resources/report_test.csv";
    private static final String EXPECTED_REPORT_TEST_FILE
            = "src/test/resources/report_expected_test.csv";

    @BeforeClass
    public static void setUp() {
        csvFileWriterService = new CsvFileWriterServiceImpl();
        reportLines = new HashMap<>();
        reportLines.put("banana","20");
        reportLines.put("apple","100");
    }

    @Test (expected = RuntimeException.class)
    public void writeDataToFileCsvTest_FilePathEmpty_NotOk() {
        csvFileWriterService.writeDataToFileCsv(reportLines,"");
    }

    @Test
    public void writeDataToFileCsvTest_WriteData_OK() {
        csvFileWriterService.writeDataToFileCsv(reportLines,REPORT_TEST_FILE);
        File actualFile = new File(REPORT_TEST_FILE);
        File expectedFile = new File(EXPECTED_REPORT_TEST_FILE);
        try {
            assertTrue("The files differ!",
                    FileUtils.contentEquals(expectedFile, actualFile));
        } catch (IOException e) {
            throw new RuntimeException("Can't write file:" + REPORT_TEST_FILE, e);
        }
    }
}
