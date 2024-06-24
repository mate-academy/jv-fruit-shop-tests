package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.CsvFileReaderService;
import core.basesyntax.service.exception.FileOperationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvFileReaderServiceImplTest {
    private final CsvFileReaderService csvFileReaderService = new CsvFileReaderServiceImpl();

    @Test
    void readFromFile_validFile_success() {
        List<String> lines = csvFileReaderService.readFromFile(
                "src/test/resources/test_input.csv");
        assertFalse(lines.isEmpty());
    }

    @Test
    void readFromFile_fileNotFound_throwsException() {
        assertThrows(FileOperationException.class, () ->
                csvFileReaderService.readFromFile("invalid/path/to/file.csv"));
    }

    @Test
    void readFromFile_emptyFile_success() {
        List<String> lines = csvFileReaderService.readFromFile(
                "src/test/resources/empty_input.csv");
        assertTrue(lines.isEmpty());
    }

    @Test
    void readFromFile_malformedFile_throwsException() {
        assertThrows(FileOperationException.class, () ->
                csvFileReaderService.readFromFile("src/test/resources/malformed_input.csv"));
    }

    @Test
    void readFromFile_largeFile_success() throws IOException {
        String filePath = "src/test/resources/large_input.csv";
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new IOException("File does not exist: " + filePath);
        }

        if (!Files.isReadable(path)) {
            throw new IOException("File is not readable: " + filePath);
        }

        Files.size(path);

        List<String> lines = csvFileReaderService.readFromFile(filePath);

        assertFalse(lines.isEmpty(), "Expected non-empty list but got empty.");
    }

    @Test
    void readFromFile_singleLineFile_success() throws IOException {
        String filePath = "src/test/resources/single_line_input.csv";
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new IOException("File does not exist: " + filePath);
        }

        List<String> lines = csvFileReaderService.readFromFile(filePath);

        assertFalse(lines.isEmpty());
        assertEquals(2, lines.size(), "Expected two lines in the file"); // header + one data line
    }

    @Test
    void readFromFile_fileWithEmptyLines_success() throws IOException {
        String filePath = "src/test/resources/file_with_empty_lines.csv";
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new IOException("File does not exist: " + filePath);
        }

        List<String> lines = csvFileReaderService.readFromFile(filePath);

        assertFalse(lines.isEmpty());
        assertTrue(lines.contains(""), "Expected empty lines in the file");
    }
}
