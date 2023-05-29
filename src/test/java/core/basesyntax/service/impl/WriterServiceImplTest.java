package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.WriterService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static WriterService writeService;

    @BeforeAll
    public static void beforeAll() {
        writeService = new WriterServiceImpl();
    }

    @Test
    void writerService_writeToFile_ok() {
        boolean operationSuccess =
                writeService.writeToFile("src/test/resources/report_file_test.csv", "report");
        assertTrue(operationSuccess);
    }

    @Test
    void writerService_invalidFilePath_notOk() {
        assertThrows(RuntimeException.class,
                () -> writeService.writeToFile("invalid/file/path.csv", "report"),
                "Should throw runtime exception.");
    }
}
