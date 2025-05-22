package basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static FileWriterInterface fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_validPathAndData_Ok() throws IOException {
        Path tempFile = Files.createTempFile("test", ".csv");

        String expectedContent = "fruit,quantity"
                + System.lineSeparator()
                + "banana,50"
                + System.lineSeparator()
                + "apple,30";
        fileWriter.write(expectedContent, tempFile.toString());
        String actualContent = Files.readString(tempFile);
        assertEquals(expectedContent, actualContent);

        Files.deleteIfExists(tempFile);
    }

    @Test
    void write_nullPath_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write("fruit,quantity"
                        + System.lineSeparator()
                        + "banana,50"
                        + System.lineSeparator()
                        + "apple,30",
                        null));
    }

    @Test
    void write_emptyPath_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write("fruit,quantity"
                        + System.lineSeparator()
                        + "banana,50"
                        + System.lineSeparator()
                        + "apple,30",
                        ""));
    }

    @Test
    void write_nonExistingDirectory_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write("fruit,quantity"
                        + System.lineSeparator()
                        + "banana,50"
                        + System.lineSeparator()
                        + "apple,30",
                        "C:/non/exist/folder/file.csv"));
    }
}
