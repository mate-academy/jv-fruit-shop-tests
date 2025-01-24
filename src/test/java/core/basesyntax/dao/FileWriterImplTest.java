package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String TEST_OUTPUT_FILE = "src/test/resources/test_output.csv";
    private Path tempFile;
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() throws IOException {
        fileWriter = new FileWriterImpl();
        tempFile = Path.of(TEST_OUTPUT_FILE);
        if (!Files.exists(tempFile)) {
            Files.createFile(tempFile);
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        if (Files.exists(tempFile)) {
            Files.delete(tempFile);
        }
    }

    @Test
    void writeFile_validReport_createsFileWithContent() throws IOException {
        String report = "fruit,quantity\napple,10\nbanana,20";
        fileWriter.writeFile(TEST_OUTPUT_FILE, report);
        String fileContent = Files.readString(tempFile);
        String[] lines = fileContent.split("\n");
        assertEquals("fruit,quantity", lines[0], "Header should match");
        assertEquals("apple,10", lines[1], "First data line should match");
        assertEquals("banana,20", lines[2], "Second data line should match");
    }

    @Test
    void writeFile_emptyReport_createsFileWithNoContent() throws IOException {
        String report = "";
        fileWriter.writeFile(TEST_OUTPUT_FILE, report);
        String fileContent = Files.readString(tempFile);
        assertEquals("", fileContent, "File content should be empty");
    }

    @Test
    void writeFile_nullFileName_throwsException() {
        String report = "fruit,quantity\napple,10";
        Exception exception = assertThrows(RuntimeException.class,
                () -> fileWriter.writeFile(null, report));
        assertEquals("File name cannot be null",
                exception.getMessage(), "Error message should match");
    }
}
