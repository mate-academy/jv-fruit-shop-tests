package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.CsvFileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CsvFileWriteServiceImplTest {
    private static final String FILE_NAME_TEST
            = "src/test/resources/testReport.csv";
    private CsvFileWriterService csvFileWriterService;
    private String reportData;

    @Before
    public void setUp() {
        csvFileWriterService = new CsvFileWriteServiceImpl();
        reportData = "fruit,quantity\\r\\norange,1520\\r\\nkiwi,900";
    }

    @Test (expected = RuntimeException.class)
    public void writeReportToFile_nullFileNAme_notOk() {
        csvFileWriterService.writeReportToFile(reportData, null);
    }

    @Test (expected = RuntimeException.class)
    public void writeReportToFile_emptyFileNAme_notOk() {
        csvFileWriterService.writeReportToFile(reportData,"");
    }

    @Test (expected = RuntimeException.class)
    public void writeReportToFile_nullReportName_notOk() {
        csvFileWriterService.writeReportToFile(null, FILE_NAME_TEST);
    }

    @Test
    public void writeReportToFile_rightFileNameAndReportData_Ok() {
        String expected = reportData;
        csvFileWriterService.writeReportToFile(reportData, FILE_NAME_TEST);
        String actual = getStringFromFile(FILE_NAME_TEST);
        assertEquals(expected, actual);
    }

    private static String getStringFromFile(String fileName) {
        List<String> actualList = new ArrayList<>();
        try {
            actualList = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file in test", e);
        }
        return actualList.get(0);
    }
}
