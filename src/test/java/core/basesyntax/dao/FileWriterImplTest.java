package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private FileWriterImpl fileWriter;

    @BeforeEach
    public void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeToFile_writeToEmptyPath_notOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                fileWriter.write("Text", ""));
    }

    @Test
    public void writeToFile_validDataAndNullPath_notOk() {
        String data = " banana - 152, apple - 90";
        String filePath = null;
        Assertions.assertThrows(RuntimeException.class, () -> fileWriter.write(data, filePath));
    }

    @Test
    public void writeToFile_emptyDataAndNullPath_notOk() {
        String data = "";
        String filePath = null;
        Assertions.assertThrows(RuntimeException.class, () -> fileWriter.write(data, filePath));
    }

    @Test
    public void writeToFile_validDataAndValidPath_ok() throws IOException {
        String data = "banana - 152, apple - 90";
        String filePath = "test.txt";
        fileWriter.write(data, filePath);
        String expected = filePath;
        String actual = new String(Files.readString(Path.of(filePath)));
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_emptyDataAndValidPath_ok() throws IOException {
        String data = "";
        String filePath = "test.txt";
        fileWriter.write(data, filePath);
        String expected = filePath;
        String actual = new String(Files.readString(Path.of(filePath)));
        assertEquals(expected, actual);
    }
}
