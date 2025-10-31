package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterTest {
    private static final String EXISTING_FILE_NAME = "finalReport.csv";
    private static final String NON_EXISTING_DIRECTORY = "/resources/finalReport.csv";
    private FileWriter fileWriter;

    @TempDir
    private Path tempDir;

    @BeforeEach
    void initializeService() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_existingPath_ok() throws IOException {
        Path tempFilePath = tempDir.resolve(EXISTING_FILE_NAME);
        String testData = "report";
        String absolutePathString = tempFilePath.toString();
        fileWriter.write(testData, absolutePathString);
        assertTrue(Files.exists(tempFilePath), "Output file should exist.");
        String content = Files.readString(tempFilePath);
        assertTrue(content.contains(testData), "File content should match test data.");
    }

    @Test
    void write_nonExistingDirectory_notOk() {
        String report = "report";
        assertThrows(RuntimeException.class, () -> fileWriter.write(report, NON_EXISTING_DIRECTORY),
                "not throwing error for non existing directory");
    }

}
