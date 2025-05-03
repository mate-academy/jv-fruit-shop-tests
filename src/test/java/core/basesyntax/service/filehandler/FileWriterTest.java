package core.basesyntax.service.filehandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class FileWriterTest {
    private static final String NONEXISTENT_FILE_PATH = "nonexistent_folder/nonexistent_file.txt";
    private static final String STORAGE_REPORT_FILE_PATH = "src/test/java/resources/TestFile.csv";
    private static final String REPORT_CONTENT = "Test report content";
    private final FileWriter writer = new FileWriter();

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(STORAGE_REPORT_FILE_PATH));
    }

    @Test
    public void write_successfulWriting_Ok() throws IOException {
        writer.write(STORAGE_REPORT_FILE_PATH, REPORT_CONTENT);

        String fileContent = new String(Files.readAllBytes(Paths.get(STORAGE_REPORT_FILE_PATH)));
        assertEquals(REPORT_CONTENT, fileContent);
    }

    @Test
    public void throw_runtimeException_notOk() {
        assertThrows(RuntimeException.class, ()
                -> writer.write(NONEXISTENT_FILE_PATH, REPORT_CONTENT));
    }
}
