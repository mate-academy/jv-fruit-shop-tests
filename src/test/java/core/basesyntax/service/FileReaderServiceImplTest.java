package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private FileReaderServiceImpl fileReaderService;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        fileReaderService = new FileReaderServiceImpl();
        tempFile = File.createTempFile("test-data", ".txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("b,banana,20\n");
            writer.write("b,apple,100\n");
            writer.write("s,banana,100\n");
            writer.write("p,banana,13\n");
            writer.write("r,apple,10\n");
            writer.write("p,apple,20\n");
            writer.write("p,banana,5\n");
            writer.write("s,banana,50\n");
        }
    }

    @Test
    void readFile_validFile_ok() {
        List<String> lines = fileReaderService.readFile(tempFile.getAbsolutePath());
        assertNotNull(lines);
        assertEquals(8, lines.size());

        assertEquals("b,banana,20", lines.get(0));
        assertEquals("b,apple,100", lines.get(1));
        assertEquals("s,banana,100", lines.get(2));
        assertEquals("p,banana,13", lines.get(3));
        assertEquals("r,apple,10", lines.get(4));
        assertEquals("p,apple,20", lines.get(5));
        assertEquals("p,banana,5", lines.get(6));
        assertEquals("s,banana,50", lines.get(7));
    }

    @Test
    void readFile_fileDoesNotExist_notOk() {
        String invalidPath = "non-existent-file.txt";
        Exception exception = assertThrows(RuntimeException.class, () ->
                fileReaderService.readFile(invalidPath));
        assertTrue(exception.getMessage().contains("Can not read the file"));
    }

    @Test
    void readFile_emptyFile_ok() throws IOException {
        File emptyFile = File.createTempFile("empty-data", ".txt");
        List<String> lines = fileReaderService.readFile(emptyFile.getAbsolutePath());
        assertNotNull(lines);
        assertTrue(lines.isEmpty());
        emptyFile.deleteOnExit();
    }
}
