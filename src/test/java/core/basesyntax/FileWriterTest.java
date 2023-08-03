package core.basesyntax;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.CsvFileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

public class FileWriterTest {
    private final String testFolderPath = "src/test/resources/";
    private FileWriter fileWriter;

    @Before
    public void setUp() throws Exception {
        this.fileWriter = new CsvFileWriterImpl();
    }

    @AfterEach
    void tearDown() {
        // Clean up test files after each test
        Path testFile = Paths.get(testFolderPath + "test.csv");
        try {
            Files.deleteIfExists(testFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to clean up test file: " + testFile, e);
        }
    }

    @Test
    public void writeToFile_ValidData_FileCreatedWithCorrectContent() {
        String fileName = testFolderPath + "test.csv";
        String report = "Test report content";
        try {
            fileWriter.writeToFile(report, fileName);
            String actualContent = Files.readString(Path.of(fileName));
            Assertions.assertEquals(report, actualContent);
        } catch (IOException e) {
            Assertions.fail("IOException occurred: " + e.getMessage());
        }
    }
}
