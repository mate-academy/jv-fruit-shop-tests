package service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

class CsvWriterServiceTest {
    private CsvWriterService csvWriterService;

    @BeforeEach
    void setUp() {
        csvWriterService = new CsvWriterService();
    }

    @Test
    void writeData_ValidData_SuccessfullyWritesToFile() throws IOException {
        String filepath = "test.csv";
        List<String> data = new ArrayList<>();
        data.add("Line 1");
        data.add("Line 2");

        csvWriterService.writeData(filepath, data);

        assertTrue(Files.exists(Path.of(filepath)));
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        assertEquals("Line 1", reader.readLine());
        assertEquals("Line 2", reader.readLine());
        reader.close();
    }

    @Test
    void writeData_EmptyData_FileIsEmpty() throws IOException {
        String filepath = "test.csv";
        List<String> data = new ArrayList<>();

        csvWriterService.writeData(filepath,data);

        assertTrue(Files.exists(Path.of(filepath)));
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        assertNull(reader.readLine());
        reader.close();
    }

    @Test
    void writeData_NullFilePath_ThrowsRuntimeException() {
        String filepath = null;
        List<String> data = new ArrayList<>();
        data.add("Line 1");
        assertThrows(RuntimeException.class, () -> csvWriterService.writeData(filepath, data));
    }

    @Test
    void writeData_IoExceptionOccurred_ThrowsRuntimeException() {
        String filepath = "/nonexistent_directory/test.csv";
        List<String> data = new ArrayList<>();
        data.add("Line 1");
        assertThrows(RuntimeException.class, () -> csvWriterService.writeData(filepath, data));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of("test.csv"));
    }
}
