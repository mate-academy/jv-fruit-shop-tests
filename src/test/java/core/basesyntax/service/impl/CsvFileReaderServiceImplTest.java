package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.CsvFileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvFileReaderServiceImplTest {
    private final CsvFileReaderService csvFileReaderService = new CsvFileReaderServiceImpl();

    @Test
    void readFromFile_validFile_success() throws IOException {
        List<String> lines = csvFileReaderService.readFromFile(
                "src/test/resources/test_input.csv");
        assertFalse(lines.isEmpty());
    }

    @Test
    void readFromFile_fileNotFound_throwsException() {
        assertThrows(NoSuchFileException.class, () ->
                csvFileReaderService.readFromFile("invalid/path/to/file.csv"));
    }

    @Test
    void readFromFile_emptyFile_success() throws IOException {
        List<String> lines = csvFileReaderService.readFromFile(
                "src/test/resources/empty_input.csv");
        assertTrue(lines.isEmpty());
    }

    @Test
    void readFromFile_malformedFile_throwsException() {
        assertThrows(IOException.class, () ->
                csvFileReaderService.readFromFile("src/test/resources/malformed_input.csv"));
    }

    @Test
    void readFromFile_largeFile_success() throws IOException {
        String filePath = "src/test/resources/large_input.csv";
        Path path = Paths.get(filePath);

        System.out.println("Checking file: " + filePath);

        if (!Files.exists(path)) {
            System.out.println("File does not exist: " + filePath);
            throw new IOException("File does not exist: " + filePath);
        }

        if (!Files.isReadable(path)) {
            System.out.println("File is not readable: " + filePath);
            throw new IOException("File is not readable: " + filePath);
        }

        long fileSize = Files.size(path);
        System.out.println("File size: " + fileSize + " bytes");

        List<String> lines = csvFileReaderService.readFromFile(filePath);
        System.out.println("Read " + lines.size() + " lines from " + filePath);
        for (String line : lines) {
            System.out.println(line);
        }

        assertFalse(lines.isEmpty(), "Expected non-empty list but got empty.");
    }

    @Test
    void readFromFile_singleLineFile_success() throws IOException {
        String filePath = "src/test/resources/single_line_input.csv";
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            System.out.println("File does not exist: " + filePath);
            throw new IOException("File does not exist: " + filePath);
        }

        List<String> lines = csvFileReaderService.readFromFile(filePath);
        System.out.println("Read " + lines.size() + " lines from " + filePath);
        for (String line : lines) {
            System.out.println(line);
        }

        assertFalse(lines.isEmpty());
        assertTrue(lines.size() == 2, "Expected two lines in the file"); // header + one data line
    }

    @Test
    void readFromFile_fileWithEmptyLines_success() throws IOException {
        String filePath = "src/test/resources/file_with_empty_lines.csv";
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            System.out.println("File does not exist: " + filePath);
            throw new IOException("File does not exist: " + filePath);
        }

        List<String> lines = csvFileReaderService.readFromFile(filePath);
        System.out.println("Read " + lines.size() + " lines from " + filePath);
        for (String line : lines) {
            System.out.println(line);
        }

        assertFalse(lines.isEmpty());
        assertTrue(lines.contains(""), "Expected empty lines in the file");
    }
}
