package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.WriteCsv;
import exception.FileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriteCsvImplTest {
    private static final String FILE_PATH = "src/main/resources/test_write.csv";
    private static final String WRONG_FILE_PATH = "src/main/strategy/test_write.csv";
    private static final String DATA_FOR_WRITING = "b,banana,40\n";
    private WriteCsv writeCsv;

    @BeforeEach
    void setUp() {
        writeCsv = new WriteCsvImpl();
    }

    @Test
    void writeToFile_Ok() {
        boolean expected = false;
        try {
            writeCsv.writeToFile(FILE_PATH, DATA_FOR_WRITING);
            expected = true;
        } catch (FileException e) {
            throw new FileException("Can`t write data to file");
        }
        assertTrue(expected);
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
                writeCsv.writeToFile(WRONG_FILE_PATH, DATA_FOR_WRITING),
                "Path to file is wrong");
    }
}
