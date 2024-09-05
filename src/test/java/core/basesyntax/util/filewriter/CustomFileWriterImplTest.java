package core.basesyntax.util.filewriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CustomFileWriterImplTest {
    private static final String TEST_FILE_PATH = "testReport.csv";
    private static final String INVALID_TEST_FILE_PATH = "/invalidPath/testReport.csv";
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "apple,100" + System.lineSeparator();

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
        customFileWriter.write(REPORT, TEST_FILE_PATH);

        Path filePath = Path.of(TEST_FILE_PATH);
        assertTrue(Files.exists(filePath),
                "File should be created at the specified path.");

        String fileContent = Files.readString(filePath);
        assertEquals(REPORT, fileContent,
                "The content written to the file should match the expected content.");
    }

    @Test
    public void write_nullContent_notOk() {
        String report = null;
        assertThrows(NullPointerException.class, () ->
                customFileWriter.write(report, TEST_FILE_PATH));
    }

    @Test
    public void write_nullFilePath_notOk() {
        String filePath = null;
        assertThrows(NullPointerException.class, () ->
                customFileWriter.write(REPORT, filePath));
    }

    @Test
    public void write_invalidFilePath_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                customFileWriter.write(REPORT, INVALID_TEST_FILE_PATH));

        assertTrue(exception.getMessage()
                .contains("Cannot write to file: " + INVALID_TEST_FILE_PATH));
    }
}
