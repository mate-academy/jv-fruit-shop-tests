package basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private FileWriterInterface fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_validPathAndData_Ok() throws IOException {
        Path tempFile = Files.createTempFile("test", ".csv");

        String expectedContent = "fruit,quantity\nbanana,50\napple,30";
        fileWriter.write(expectedContent, tempFile.toString());
        String actualContent = Files.readString(tempFile);
        assertEquals(expectedContent, actualContent);

        Files.deleteIfExists(tempFile);
    }

    @Test
    void write_nullPath_NotOk() {
        String expectedContent = "fruit,quantity\nbanana,50\napple,30";
        assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write(expectedContent, null));
    }

    @Test
    void write_emptyPath_NotOk() {
        String expectedContent = "fruit,quantity\nbanana,50\napple,30";
        assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write(expectedContent, ""));
    }

    @Test
    void write_nonExistingDirectory_NotOk() {
        String expectedContent = "fruit,quantity\nbanana,50\napple,30";
        String invalidPath = "C:/non/exist/folder/file.csv";

        assertThrows(RuntimeException.class,
                () -> fileWriter.write(expectedContent, invalidPath));
    }
}
