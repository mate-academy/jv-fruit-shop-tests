package core.basesyntax.service.filehandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterTest {
    private static final String NONEXISTENT_FILE_PATH = "nonexistent_folder/nonexistent_file.txt";
    private static final String STORAGE_REPORT_FILE_PATH = "src/test/java/resources/TestFile.csv";
    private FileWriter writer;
    private final String reportText = "Test report content";

    @BeforeEach
    void setUp() {
        writer = new FileWriter();
    }

    @Test
    public void write_WriteToFile_Ok() throws IOException {
        writer.write(STORAGE_REPORT_FILE_PATH, reportText);

        String fileContent = new String(Files.readAllBytes(Paths.get(STORAGE_REPORT_FILE_PATH)));
        assertEquals(reportText, fileContent);
    }

    @Test
    public void throw_RuntimeException_Ok() {
        assertThrows(RuntimeException.class, () -> writer.write(NONEXISTENT_FILE_PATH, reportText));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(STORAGE_REPORT_FILE_PATH));
    }
}
