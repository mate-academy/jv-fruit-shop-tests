package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.WriterService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static WriterService writeService;
    private static final String MASSAGE = "Should throw runtime exception.";

    @BeforeAll
    public static void beforeAll() {
        writeService = new WriterServiceImpl();
    }

    @Test
    void writerService_writeToFile_ok() {
        boolean operationSuccess =
                writeService.writeToFile("src/main/resources/report_file.csv", "report");
        assertTrue(operationSuccess);
    }

    @Test
    void writerService_invalidFilePath_notOk() {
        assertThrows(RuntimeException.class,
                () -> writeService.writeToFile("invalid/file/path.csv", "report"),
                MASSAGE);
    }
}
