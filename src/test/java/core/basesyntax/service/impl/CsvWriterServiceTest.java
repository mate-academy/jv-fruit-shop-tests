package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterService;
import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvWriterServiceTest {
    private static final String VALID_FILE_PATH = "src/test/resources/test-report.csv";
    private static final String REPORT = "type, quantity" + System.lineSeparator()
            + "banana, 100" + System.lineSeparator()
            + "apple, 100" + System.lineSeparator();
    private static File file;
    private WriterService writer;

    @BeforeEach
    void setUp() {
        writer = new CsvWriterService();
        file = new File(VALID_FILE_PATH);
    }

    @AfterEach
    void tearDown() {
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeValidFileAndData_Ok() {
        assertDoesNotThrow(() -> writer.write(VALID_FILE_PATH, REPORT));
    }

    @Test
    void writeToNullFile_NotOk() {
        assertThrows(NullPointerException.class, () -> writer.write(null, REPORT));
    }

    @Test
    void writeNullContent_NotOk() {
        assertThrows(NullPointerException.class, () -> writer.write(VALID_FILE_PATH, null));
    }
}
