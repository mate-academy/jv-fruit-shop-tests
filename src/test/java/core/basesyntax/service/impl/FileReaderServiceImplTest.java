package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static final String INVALID_FILE_PATH = "";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty-file.csv";
    private static final String EXAMPLE_FILE_PATH = "src/test/resources/example-file.csv";
    private FileReaderServiceImpl fileReaderService;

    @BeforeEach
    void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @DisplayName("Checking for passing null as filePath value")
    @Test
    void readLinesFromFile_nullFilePath_notOk() {
        assertThrows(NullPointerException.class, () ->
                fileReaderService.readLinesFromFile(null));
    }

    @DisplayName("Checking for passing path for file, that doesn't exist")
    @Test
    void readLinesFromFile_invalidFilePath_notOk() {
        assertThrows(RuntimeException.class, () ->
                fileReaderService.readLinesFromFile(INVALID_FILE_PATH));
    }

    @DisplayName("Checking for reading empty file")
    @Test
    void readLinesFromFile_emptyFile_ok() {
        List<String> expected = Collections.emptyList();
        List<String> actual = fileReaderService.readLinesFromFile(EMPTY_FILE_PATH);
        assertEquals(expected, actual);
    }

    @DisplayName("Checking for reading example data")
    @Test
    void readLinesFromFile_exampleData_ok() {
        List<String> expected = readLines(EXAMPLE_FILE_PATH);
        List<String> actual = fileReaderService.readLinesFromFile(EXAMPLE_FILE_PATH);
        assertEquals(expected, actual);
    }

    private static List<String> readLines(String filePath) {
        try (Stream<String> stream = Files.lines(Path.of(filePath))) {
            return stream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Cannot find file by path: " + filePath, e);
        }
    }
}
