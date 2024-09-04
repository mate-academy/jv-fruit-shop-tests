package core.basesyntax.util.filewriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;


public class CustomFileWriterImplTest {
    private static final String TEST_FILE_PATH = "testReport.csv";
    private static CustomFileWriter customFileWriter;

    @BeforeAll
    static void setUp() {
        customFileWriter = new CustomFileWriterImpl();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE_PATH));
    }

    @Test
    public void write_validContent_ok() throws IOException {
        String report = "fruit,quantity" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();

        customFileWriter.write(report, TEST_FILE_PATH);

        Path filePath = Path.of(TEST_FILE_PATH);
        assertTrue(Files.exists(filePath),
                "File should be created at the specified path.");

        String fileContent = Files.readString(filePath);
        assertEquals(report, fileContent,
                "The content written to the file should match the expected content.");
    }

    @Test
    public void write_nullContent_notOk() {
        String report = null;
        assertThrows(NullPointerException.class, () ->
                customFileWriter.write(report, TEST_FILE_PATH));
    }
}
