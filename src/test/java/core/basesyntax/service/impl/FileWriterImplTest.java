package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterImplTest {
    private FileWriter fileWriter;
    private Path testFile;

    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
        testFile = tempDir.resolve("test_output.txt");
    }

    @Test
    void write_validData_fileContainsCorrectContent() throws IOException {
        String testData = "Hello, FileWriter!";
        fileWriter.write(testData, testFile.toString());

        String result = Files.readString(testFile);
        assertEquals(testData, result, "File content should match the written data.");
    }

    @Test
    void write_invalidPath_throwsException() {
        String invalidPath = "/invalid/path/test.txt";
        assertThrows(RuntimeException.class, () -> fileWriter.write("Data", invalidPath));
    }
}
