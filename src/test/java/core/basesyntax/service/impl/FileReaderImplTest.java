package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void readFromFile_correctPath_Ok() {
        assertDoesNotThrow(() -> {
            fileReader.readFromFile("src/main/resources/reportToRead.csv");
        });
    }

    @Test
    void readFromFile_wrongPath_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.readFromFile("incorrectPath.csv");
        });
    }
}
