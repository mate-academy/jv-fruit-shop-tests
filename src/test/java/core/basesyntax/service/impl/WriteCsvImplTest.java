package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriteCsv;
import exception.FileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriteCsvImplTest {
    private static final String FILE_PATH = "src/main/resources/test_write.csv";
    private static final String NOT_EXISTING_FILE_PATH =
            "src/main/service/not_existing_file.csv";
    private static final String DATA_FOR_WRITING = "b,banana,40\n";
    private WriteCsv writeCsv;

    @BeforeEach
    void setUp() {
        writeCsv = new WriteCsvImpl();
    }

    @Test
    void writeToFile_Ok() {
        assertDoesNotThrow(() -> writeCsv.writeToFile(FILE_PATH, DATA_FOR_WRITING),
                "File path is not valid " + FILE_PATH);
    }

    @Test
    void writeNull_NotOk() {
        assertThrows(NullPointerException.class, () ->
                writeCsv.writeToFile(FILE_PATH, null),
                "Data can`t be null.");
    }

    @Test
    void wrongPath_NotOK() {
        assertThrows(FileException.class, () ->
                writeCsv.writeToFile(NOT_EXISTING_FILE_PATH, DATA_FOR_WRITING),
                "Path to file is wrong");
    }
}
