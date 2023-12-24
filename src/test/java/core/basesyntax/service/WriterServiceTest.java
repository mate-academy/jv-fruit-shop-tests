package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.WriterServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceTest {
    private static final String WRONG_FILE_PATH = "src/wrong/path";
    private static final String FILE_PATH = "src/test/resources/writingTest.csv";
    private static final String TEST_REPORT = "fruit,quantity\n"
                                                + "apple,10\n"
                                                + "mango,15\n"
                                                + "coconut,3";
    private static WriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeToFile_emptyFile_ok() {
        assertDoesNotThrow(() -> writerService.writeToFile(FILE_PATH, ""));
    }

    @Test
    void writeToFile_defaultCase_ok() {
        assertDoesNotThrow(() -> writerService.writeToFile(FILE_PATH, TEST_REPORT));
    }

    @Test
    void writeToFile_wrongPath_notOk() {
        assertThrows(NullPointerException.class,
                () -> writerService.writeToFile(null, TEST_REPORT));
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(WRONG_FILE_PATH, TEST_REPORT));

    }
}
