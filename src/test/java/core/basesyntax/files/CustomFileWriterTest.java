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
    private static final String FILE_TO_WRITE = "src\\main\\resources\\finalReport.csv";
    private CustomFileWriter customFileWriter;
    private Path filePath;
    private String somePath;

    @BeforeEach
    void setUp() {
        customFileWriter = new FileWriterImpl();
        customFileWriter.write(FILE_TO_WRITE, "fruit,quantity\n"
                + "banana,152\n"
                + "apple,90");
        filePath = Paths.get(FILE_TO_WRITE);
        somePath = filePath.toString();
    }

    @Test
    void writer_contentCheck_ok() throws IOException {
        String expectedContent = "fruit,quantity\n"
                + "banana,152\n"
                + "apple,90";
        customFileWriter.write(somePath,expectedContent);

        String readString = Files.readString(filePath);

        assertEquals(expectedContent, readString);
    }

    @Test
    void writer_invalidPath_notOk() {
        String invalidPath = "non_writable_directory\\report.csv";
        String content = "some data content";

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customFileWriter.write(invalidPath, content);
        });

        assertEquals("Error writing to the file " + invalidPath,exception.getMessage());
    }
}
