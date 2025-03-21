package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.CustomFileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileWriterImplTest {
    private static final String DEFAULT_FILE_PATH = "src/main/resources/finalReport.csv";
    private static final String TEST_FILE_NAME = "test_output.csv";

    private CustomFileWriter fileWriter;

    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl(tempDir.resolve(TEST_FILE_NAME).toString());
    }

    @Test
    void write_ValidReport_ShouldWriteToFile() throws IOException {
        String expectedReport = "fruit,quantity\napple,10\nbanana,5\n";

        fileWriter.write(expectedReport);

        String actualContent = Files.readString(tempDir.resolve(TEST_FILE_NAME));
        assertEquals(expectedReport, actualContent);
    }

    @Test
    void write_NullReport_ShouldThrowException() {
        assertThrows(RuntimeException.class, () -> fileWriter.write(null));
    }

    @Test
    void write_EmptyReport_ShouldCreateEmptyFile() throws IOException {
        String expectedReport = "";

        fileWriter.write(expectedReport);

        String actualContent = Files.readString(tempDir.resolve(TEST_FILE_NAME));
        assertEquals(expectedReport, actualContent);
    }

    @Test
    void write_ValidReport_ShouldCreateFileAtDefaultLocation_WhenNoCustomPathUsed()
            throws IOException {
        fileWriter = new FileWriterImpl();
        String expectedReport = "fruit,quantity\napple,10\nbanana,5\n";

        fileWriter.write(expectedReport);

        Path defaultFilePath = Path.of(DEFAULT_FILE_PATH);
        String actualContent = Files.readString(defaultFilePath);
        assertEquals(expectedReport, actualContent);
    }

    @Test
    void write_ShouldThrowIoException_WhenPathIsInvalid() {
        fileWriter = new FileWriterImpl("invalid/path/to/file.csv");

        assertThrows(RuntimeException.class,
                () -> fileWriter.write("fruit,quantity\napple,10\nbanana,5\n"));
    }
}
