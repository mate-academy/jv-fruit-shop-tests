package core.basesyntax.files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class FilesWriterImplTest {
    private static final String TEST_FILE_PATH = "src/test/resources/report.csv";
    private static FilesWriter writer;

    @Test
    void writing_WritesDataToFile_ok() throws IOException {
        writer = new FilesWriterImpl();
        String report = "Test report data";
        writer.writeToFile(TEST_FILE_PATH,report);

        String content = new String(Files.readAllBytes(Paths.get(TEST_FILE_PATH)));
        assertEquals(report, content);
    }

    @Test
    void writing_WrongPath_notOk() {
        writer = new FilesWriterImpl();
        String path = "src/test/java/resour/re.csv";

        assertThrows(RuntimeException.class, () -> writer.writeToFile(path,"apple"));
    }
}
