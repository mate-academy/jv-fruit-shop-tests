package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReaderService fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void testReadFromFile_Ok() throws IOException {
        Path tempFile = Files.createTempFile("test", ".txt");
        String testData = "type,fruit,quantity\nb,pineapple,78\ns,banana,25";
        Files.writeString(tempFile, testData);
        List<String> lines = fileReader.readFromFile(tempFile);
        assertEquals(3, lines.size());
        assertEquals("type,fruit,quantity", lines.get(0));
        assertEquals("b,pineapple,78", lines.get(1));
        assertEquals("s,banana,25", lines.get(2));
        Files.deleteIfExists(tempFile);
    }

    @Test
    void readFromFile_emptyFile_OK() throws IOException {
        Path path = Files.createTempFile("test", ".txt");
        FileReaderService fileReaderService = new FileReaderImpl();
        List<String> lines = fileReaderService.readFromFile(path);
        assertTrue(lines.isEmpty());
        Files.deleteIfExists(path);
    }

    @Test
    void testReadFromFile_FileNotFound() {
        Path nonExistentFile = Path.of("non_existent_file.txt");
        assertThrows(RuntimeException.class, () -> fileReader.readFromFile(nonExistentFile));
    }

    @Test
    void testReadFromFile_IO_Error() {
        Path unreadableFile = Path.of("/unreadable/file.txt");
        assertThrows(RuntimeException.class, () -> fileReader.readFromFile(unreadableFile));
    }

    @Test
    void readFromFile_nullPath_throwsNullPointerException() {
        FileReaderService fileReaderService = new FileReaderImpl();
        assertThrows(NullPointerException.class, () -> fileReaderService.readFromFile(null));
    }
}
