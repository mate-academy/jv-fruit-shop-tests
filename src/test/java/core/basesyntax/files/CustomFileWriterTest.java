package core.basesyntax.files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomFileWriterTest {
    private static final String FILE_TO_WRITE = "src/main/resources/finalReport.csv";
    private CustomFileWriter customFileWriter;
    private Path filePath;
    private String somePath;

    @BeforeEach
    void setUp() {
        customFileWriter = new FileWriterImpl();
        filePath = Paths.get(FILE_TO_WRITE);
        somePath = filePath.toString();
    }

    @Test
    void writer_contentCheck_ok() throws IOException {
        String expectedContent = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        customFileWriter.write(somePath, expectedContent);

        String readString = Files.readString(filePath);

        assertEquals(expectedContent, readString);
    }

    @Test
    void writer_invalidPath_notOk() {
        String invalidPath = "/non_existing_directory/test_file";
        String content = "some data content";

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customFileWriter.write(invalidPath, content);
        });

        assertEquals("Error writing to the file "
                        + invalidPath,
                exception.getMessage());
    }
}
