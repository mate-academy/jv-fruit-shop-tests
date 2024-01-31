package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FileWriterService;

public class FileWriterServiceTest {
    private static final String FILE_NAME = "test_file";
    private static final String FILE_TYPE = ".csv";
    private FileWriterService fileWriterService;
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        fileWriterService = new FileWriterService();
        tempFile = Files.createTempFile(FILE_NAME, FILE_TYPE);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testWriteToFile_WithValidInput() throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("apple");
        lines.add("banana");
        fileWriterService.writeToFile(lines, tempFile.toString());
        List<String> actualLines = readLinesFromFile(tempFile.toString());
        assertEquals(lines, actualLines);
    }

    @Test
    void testWriteToFile_WithEmptyInput() throws IOException {
        List<String> lines = new ArrayList<>();
        fileWriterService.writeToFile(lines, tempFile.toString());
        List<String> actualLines = readLinesFromFile(tempFile.toString());
        assertEquals(lines, actualLines);
    }

    private List<String> readLinesFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
