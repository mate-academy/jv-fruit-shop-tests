package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String EMPTY_FILE_NAME = "";
    private static final String REPORT = "fruit,quantity\n"
            + "banana,152\n"
            + "apple,90";
    private static final String READ_FILE_PATH = "src/test/resources/file.csv";
    private static final String WRITE_FILE_PATH = "src/test/resources/file.csv";
    private static WriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void write_emptyFileName_notOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(REPORT, EMPTY_FILE_NAME));
    }

    @Test
    void write_nullFileName_notOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(REPORT, null));
    }

    @Test
    void write_validData_Ok() {
        writerService.writeToFile(REPORT, WRITE_FILE_PATH);
        try {
            String content = new String(Files.readAllBytes(Paths.get(READ_FILE_PATH)));
            assertEquals(REPORT, content);
        } catch (IOException e) {
            fail("Error reading from file: " + e.getMessage());
        }
    }
}
