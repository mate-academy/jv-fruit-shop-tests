package core.basesyntax;

import core.basesyntax.impl.FileReaderImpl;
import core.basesyntax.service.FileReader;
import org.junit.jupiter.api.BeforeEach;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


class FileReaderImplTest {
    private FileReader fileReader;
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        fileReader = new FileReaderImpl();
        tempFile = Files.createTempFile("testFile", ".txt");
    }

    @Test
    void read_ValidFile_ReturnsCorrectData() throws IOException {
        Files.write(tempFile, List.of("line1", "line2", "line3"));
        List<String> result = fileReader.read(tempFile.toString());

        assertEquals(3, result.size());
        assertEquals("line1", result.get(0));
        assertEquals("line2", result.get(1));
        assertEquals("line3", result.get(2));
    }

    @Test
    void read_EmptyFile_ReturnsEmptyList() {
        List<String> result = fileReader.read(tempFile.toString());
        assertTrue(result.isEmpty());
    }

    @Test
    void read_FileNotFound_ThrowsRuntimeException() {
        String invalidPath = "non_existing_file.txt";
        Exception exception = assertThrows(RuntimeException.class, () -> fileReader.read(invalidPath));

        assertTrue(exception.getMessage().contains("Error reading the file"));
    }

    @Test
    void read_FileWithBlankLines_HandlesCorrectly() throws IOException {
        Files.write(tempFile, List.of("line1", "", "line2", " ", "line3"));
        List<String> result = fileReader.read(tempFile.toString());

        assertEquals(5, result.size());
        assertEquals("", result.get(1));
        assertEquals(" ", result.get(3));
    }
}
