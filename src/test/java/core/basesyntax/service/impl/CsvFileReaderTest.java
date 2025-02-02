package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
    @Test
    void read_validFile_returnsCorrectData() throws IOException {
        File tempFile = File.createTempFile("test", ".csv");
        tempFile.deleteOnExit();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("line1\nline2\nline3");
        }
        CsvFileReader fileReader = new CsvFileReader();
        List<String> result = fileReader.read(tempFile.getAbsolutePath());
        assertEquals(List.of("line1", "line2", "line3"), result);
    }

    @Test
    void read_fileDoesNotExist_throwsRuntimeException() {
        CsvFileReader fileReader = new CsvFileReader();
        String nonExistentFilePath = "nonexistent.csv";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileReader.read(nonExistentFilePath));
        assertEquals("Cannot read file: " + nonExistentFilePath, exception.getMessage());
    }

    @Test
    void read_emptyFile_returnsEmptyList() throws IOException {
        File tempFile = File.createTempFile("test", ".csv");
        tempFile.deleteOnExit();
        CsvFileReader fileReader = new CsvFileReader();
        List<String> result = fileReader.read(tempFile.getAbsolutePath());
        assertEquals(List.of(), result);
    }

    @Test
    void read_fileWithBlankLines_preservesBlankLines() throws IOException {
        File tempFile = File.createTempFile("test", ".csv");
        tempFile.deleteOnExit();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("line1\n\nline3\n");
        }
        CsvFileReader fileReader = new CsvFileReader();
        List<String> result = fileReader.read(tempFile.getAbsolutePath());
        assertEquals(List.of("line1", "", "line3"), result);
    }
}
