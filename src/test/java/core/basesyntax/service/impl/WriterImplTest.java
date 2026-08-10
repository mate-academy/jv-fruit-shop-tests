package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class WriterImplTest {
    private final Writer writer = new WriterImpl();
    private final String path = "src/main/resources/testReport.csv";
    private final String report = "test123";

    @AfterEach
    void tearDown() {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            System.out.println("Could not delete file: " + e.getMessage());
        }
    }

    @Test
    void writeToFile_fileWritten_ok() {
        writer.writeToFile(path, report);
        assertTrue(Files.exists(Path.of(path)), "File was not written");
    }

    @Test
    void writeToFile_pathIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> writer.writeToFile(null,
                report), "Runtime exception expected");
    }

    @Test
    void writeToFile_pathDoesNotExist_notOk() {
        String wrongPath = "src/main/resourceses/testReport.csv";
        assertThrows(RuntimeException.class, () -> writer.writeToFile(wrongPath,
                report), "Runtime exception expected");
    }

    @Test
    void writeToFile_dataIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> writer.writeToFile(path,
                null), "Runtime exception expected");
    }
}
