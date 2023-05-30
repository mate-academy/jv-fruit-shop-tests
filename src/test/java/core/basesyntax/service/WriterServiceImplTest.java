package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.WriterServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static WriterService writerService;
    private static String report;
    private String pathToFile;

    @BeforeAll
    static void setUp() {
        writerService = new WriterServiceImpl();
        report = "fruit,quantity" + System.lineSeparator()
                + "banana, 152" + System.lineSeparator()
                + "apple, 90";
    }

    @Test
    void writerService_invalidFilePath_notOk() {
        pathToFile = "src/test/resources-invalidPath";
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> writerService.writeReportToCsvFile(report, pathToFile));
        Assertions.assertEquals("The file does not exist" + pathToFile,
                runtimeException.getMessage());
    }

    @Test
    void readerService_nullPath_notOk() {
        pathToFile = null;
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> writerService.writeReportToCsvFile(report, pathToFile));
        Assertions.assertEquals("File path cannot be null", exception.getMessage());
    }

    @Test
    void readerService_validPath_Ok() {
        pathToFile = "src/test/resources/report";
        Assertions.assertDoesNotThrow(() -> writerService
                .writeReportToCsvFile(report, pathToFile));
    }
}
