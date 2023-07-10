package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WritingFileService;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    public static final String report = "fruit,quantity"
            + "banana,152"
            + "apple,90";
    private static final String INVALID_FILE_PATH
            = "*src/test/java/resources/invalid_fruits_data.csv";
    private final WritingFileService writingFileService = new WritingFileServiceImpl();

    @Test
    void testGenerateReport_Ok() {
        assertEquals("fruit,quantity" + System.lineSeparator(),
                (new ReportServiceImpl()).generateReport());
    }

    @Test
    void writeReportToFileWriterServiceWrongPath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> writingFileService.writingDataToFile(report, INVALID_FILE_PATH));
    }
}

