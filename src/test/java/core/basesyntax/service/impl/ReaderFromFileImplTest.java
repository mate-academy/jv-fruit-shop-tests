package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderFromFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderFromFileImplTest {
    private static final String NON_EXISTENT_FILE_NAME =
            "src\\test\\resources\\Non-existent file.csv";
    private ReaderFromFile reader;

    @BeforeEach
    void setUp() {
        reader = new ReaderFromFileImpl();
    }

    @Test
    void readNonExistentFile_NotOk() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reader.readFile(NON_EXISTENT_FILE_NAME);
        });

        assertEquals("Can't read data from file " + NON_EXISTENT_FILE_NAME, exception.getMessage());
    }
}
