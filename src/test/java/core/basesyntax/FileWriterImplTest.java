package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.FileWriterImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private static FileWriter writer;

    @BeforeAll
    static void beforeAll() {
        writer = new FileWriterImpl();
    }

    @Test
    void write_validReport_Ok() throws IOException {
        File tempFile = File.createTempFile("test-file", "csv");
        tempFile.deleteOnExit();
        String report = "report";
        writer.write(report, tempFile.getAbsolutePath());
        String fileContent = new String(Files.readAllBytes(Path.of(tempFile.getAbsolutePath())));
        assertEquals(report,fileContent);
    }

    @Test
    void write_emptyFile_Ok() throws IOException {
        File tempFile = File.createTempFile("test-file", "csv");
        tempFile.deleteOnExit();
        writer.write("", tempFile.getAbsolutePath());
        String fileContent = new String(Files.readAllBytes(Path.of(tempFile.getAbsolutePath())));
        assertEquals("", fileContent);
    }

    @Test
    void write_nullReport_notOk() throws IOException {
        File tempFile = File.createTempFile("test-file", "csv");
        tempFile.deleteOnExit();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> writer.write(null, tempFile.getAbsolutePath()));
        assertEquals("Cannot write a null report!", exception.getMessage());
    }

    @Test
    void write_invalidPath_notOk() {
        String invalidPath = "/invalid/file/path/test.csv";
        String report = "valid report";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> writer.write(report, invalidPath));
        assertEquals("Error occurred while writing data to the file: "
                + invalidPath, exception.getMessage());
    }
}
