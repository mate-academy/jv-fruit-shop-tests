package core.basesyntax;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.CsvFileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private final String testFolderPath = "src/test/resources/";
    private FileWriter fileWriter;

    @BeforeEach
    public void setUp() throws Exception {
        this.fileWriter = new CsvFileWriterImpl();
    }

    @Test
    void writeToFile_validData_fileCreatedWithCorrectContent() throws IOException {
        String fileName = testFolderPath + "test1.csv";
        String report = "Test report content";
        fileWriter.writeToFile(report, fileName);
        Path path = Path.of(fileName);
        String actualContent = Files.readString(path);
        Assertions.assertEquals(report, actualContent);
        Files.deleteIfExists(path);
    }

    @Test
    void writeToFile_invalidPath_notOk() {
        String invalidPath = "non_existent_folder/test2.csv";
        String report = "Invalid path test";
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWriter.writeToFile(report, invalidPath));
    }
}
