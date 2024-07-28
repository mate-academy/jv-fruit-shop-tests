package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void testReadValidFile() throws IOException {
        Path tempFile = Files.createTempFile("testFile", ".txt");
        Files.write(tempFile, List.of("line1", "line2", "line3"));

        List<String> lines = fileReader.read(tempFile.toString());

        assertEquals(3, lines.size(), "File should have 3 lines");
        assertEquals("line1", lines.get(0), "First line should be 'line1'");
        assertEquals("line2", lines.get(1), "Second line should be 'line2'");
        assertEquals("line3", lines.get(2), "Third line should be 'line3'");

        Files.deleteIfExists(tempFile);
    }

    @Test
    void testReadNonExistentFile() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            fileReader.read("non_existent_file.txt");
        });

        String expectedMessage = "Can't read file by path:";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage),
                "Exception message should contain 'Can't read file by path:'");
    }
}
