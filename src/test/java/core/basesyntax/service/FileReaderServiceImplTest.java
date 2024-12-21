package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private FileReaderServiceImpl fileReaderService;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        fileReaderService = new FileReaderServiceImpl();
        tempFile = File.createTempFile("resources/test-data", ".csv");
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

    @AfterEach
    void tearDown() {
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void readFile_validFile_ok() {
        List<String> expectedLines = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );

        List<String> lines = fileReaderService.readFile(tempFile.getAbsolutePath());

        assertNotNull(lines);
        assertEquals(expectedLines, lines);
    }

    @Test
    void readFile_fileDoesNotExist_notOk() {
        String invalidPath = "non-existent-file.csv";
        Exception exception = assertThrows(RuntimeException.class, () ->
                fileReaderService.readFile(invalidPath));
        assertEquals("Can not read the file", exception.getMessage());
    }

    @Test
    void readFile_emptyFile_ok() throws IOException {
        File emptyFile = File.createTempFile("empty-data", ".txt");
        try {
            List<String> lines = fileReaderService.readFile(emptyFile.getAbsolutePath());
            assertNotNull(lines);
            assertTrue(lines.isEmpty());
        } finally {
            emptyFile.delete();
        }
    }
}
