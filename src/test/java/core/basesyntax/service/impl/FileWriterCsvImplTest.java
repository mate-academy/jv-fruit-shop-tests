package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.FileWriterCsvImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterCsvImplTest {
    @TempDir
    private Path tempDir;
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterCsvImpl();
    }

    @Test
    void writeToFile_ValidPath_Ok() throws IOException {
        String content = "Test content for valid path";
        Path filePath = tempDir.resolve("testFile.csv");
        fileWriter.writeToFile(content, filePath.toString());

        assertTrue(Files.exists(filePath), "File should exist after writing.");
        assertEquals(content, Files.readString(filePath),
                "Content should match the written content.");
    }

    @Test
    void writeToFile_InvalidPath_NotOk() {
        String content = "Test content for invalid path";
        String invalidPath = "\0invalidFileName.csv";
        
        assertThrows(RuntimeException.class, () -> fileWriter.writeToFile(content, invalidPath),
                "A RuntimeException should be thrown when trying to write to an invalid path.");
    }
}
