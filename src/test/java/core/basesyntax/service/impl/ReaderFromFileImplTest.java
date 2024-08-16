package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderFromFile;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderFromFileImplTest {
    private static final String READ_FILE_NAME_OK = "src\\test\\resources\\Reader Test1.csv";
    private static final String NON_EXISTENT_FILE_NAME =
            "src\\test\\resources\\Non-existent file.csv";
    private static final String TEST_LINE_ONE = "Line 1";
    private static final String TEST_LINE_TWO = "Line 2";
    private static final String TEST_LINE_THREE = "Line 3";
    private ReaderFromFile reader;

    @BeforeEach
    void setUp() {
        reader = new ReaderFromFileImpl();
    }

    @Test
    void readFile_Ok() {
        List<String> expectedContent = List.of(TEST_LINE_ONE, TEST_LINE_TWO, TEST_LINE_THREE);
        List<String> actualContent = reader.readFile(READ_FILE_NAME_OK);
        assertEquals(expectedContent, actualContent);
    }

    @Test
    void readNonExistentFile_NotOk() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reader.readFile(NON_EXISTENT_FILE_NAME);
        });

        assertEquals("Can't read data from file " + NON_EXISTENT_FILE_NAME, exception.getMessage());
    }
}
