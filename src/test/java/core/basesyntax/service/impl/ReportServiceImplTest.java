package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReportService;
import core.basesyntax.service.WritingFileService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    public static final String report = "fruit,quantity"
            + "banana,152"
            + "apple,90";
    private static final String INVALID_FILE_PATH
            = "*src/test/java/resources/invalid_fruits_data.csv";
    private static WritingFileService writingFileService;
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        writingFileService = new WritingFileServiceImpl();
        reportService = new ReportServiceImpl();
    }

    @Test
    void testGenerateReport_Ok() {
        assertEquals("fruit,quantity" + System.lineSeparator(),
                reportService.generateReport());
    }

    @Test
    void writeReportToFileWriterServiceWrongPath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> writingFileService.writingDataToFile(report, INVALID_FILE_PATH));
    }
}
