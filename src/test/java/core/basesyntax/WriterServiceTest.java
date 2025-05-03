package core.basesyntax;

import static org.junit.Assert.assertThrows;

import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.WriterServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriterServiceTest {
    private static final String correctFilePath = "src/main/resources/inputFile.csv";
    private static final String notCsvFilePath1 = "src/main/resources/inputFile.doc";
    private static final String notCsvFilePath2 = "src/main/resources/inputFile.docx";
    private static final String notCsvFilePath3 = "?";
    private static final String notCsvFilePath4 = ",.,.";
    private static final String report = "This is report";
    private static WriterService writerService;

    @BeforeAll
    static void initializeReaderService() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writerService_filePath_null_notOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeToCsv(report, null));
    }

    @Test
    void writerService_filePath_notCsv_notOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeToCsv(report, notCsvFilePath1));
        assertThrows(RuntimeException.class,
                () -> writerService.writeToCsv(report, notCsvFilePath2));
        assertThrows(RuntimeException.class,
                () -> writerService.writeToCsv(report, notCsvFilePath3));
        assertThrows(RuntimeException.class,
                () -> writerService.writeToCsv(report, notCsvFilePath4));
    }

    @Test
    void writerService_report_null_notOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeToCsv(null, correctFilePath));
    }
}
