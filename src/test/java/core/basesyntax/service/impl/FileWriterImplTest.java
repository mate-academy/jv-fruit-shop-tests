package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void testWriteValidData() throws IOException {
        Path tempFile = Files.createTempFile("testFile", ".txt");
        String data = "This is a test data";

        fileWriter.write(data, tempFile.toString());

        List<String> lines = Files.readAllLines(tempFile);
        assertEquals(1, lines.size(), "File should have 1 line");
        assertEquals(data, lines.get(0),
                "The content of the file should match the written data");

        Files.deleteIfExists(tempFile);
    }

    @Test
    void testWriteToInvalidPath() {
        String data = "This is a test data";
        String invalidPath = "/invalid_path/testFile.txt";

        Exception exception = assertThrows(RuntimeException.class, () -> {
            fileWriter.write(data, invalidPath);
        });

        String expectedMessage = "Can't write data to file: " + invalidPath;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage),
                "Exception message should contain 'Can't write data to file: " + invalidPath + "'");
    }
}
