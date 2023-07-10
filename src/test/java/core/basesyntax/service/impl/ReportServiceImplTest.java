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
            = "src/test/java/resources/invalid_fruits_data.csv";
    private static WritingFileService writingFileService;

    @Test
    void testGenerateReport_Ok() {
        assertEquals("fruit,quantity\r\n", (new ReportServiceImpl()).generateReport());
    }

    @Test
    void writeReportToFileWriterServiceWrongPath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> writingFileService.writingDataToFile(INVALID_FILE_PATH, report));
    }
}

