package core.basesyntax.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.FileOperationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
    private static final String FILE_PATH = "src/test/resources/test.csv";
    private static final String INVALID_PATH = "nonexistent/test.csv";
    private CsvFileWriter writer;

    @BeforeEach
    void setUp() throws IOException {
        writer = new CsvFileWriter();
        Files.deleteIfExists(Path.of(FILE_PATH));
    }

    @Test
    void write_ValidDataToFile_Ok() throws IOException {
        String expected = "fruit,quantity\nbanana,152\napple,90";
        writer.write(expected, FILE_PATH);
        String actual = Files.readString(Path.of(FILE_PATH));
        assertEquals(expected, actual);
    }

    @Test
    void write_InvalidPath_ThrowsException_NotOk() {
        String data = "fruit,quantity\nbanana,152\napple,90";
        assertThrows(FileOperationException.class, () -> writer.write(data, INVALID_PATH));
    }
}
