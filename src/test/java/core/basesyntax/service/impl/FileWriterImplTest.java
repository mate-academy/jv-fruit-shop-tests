package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String correctPath = "correctPath.csv";
    private static final String invalidPath = "C:/invalid_path/file.csv";
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void writeToFile_reportIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileWriter.writeToFile(null, correctPath);
        });
    }

    @Test
    void writeToFile_wrongPath_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileWriter.writeToFile("Hello, world!", invalidPath);
        });
    }

    @Test
    void writeToFile_correctDate_Ok() {
        assertDoesNotThrow(() -> fileWriter
                .writeToFile("Correct Report", correctPath));
    }
}
