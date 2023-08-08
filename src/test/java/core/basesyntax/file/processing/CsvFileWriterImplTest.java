package core.basesyntax.file.processing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class CsvFileWriterImplTest {
    private static final String TEST_FILE_PATH = "src/test/java/resources/report.csv";
    private static CsvFileWriter writer;

    @Test
    void writing_WritesDataToFile_ok() throws IOException {
        writer = new CsvFileWriterImpl(TEST_FILE_PATH);
        String report = "Test report data";
        writer.writing(report);

        String content = new String(Files.readAllBytes(Paths.get(TEST_FILE_PATH)));
        assertEquals(report, content);
    }

    @Test
    void writing_WrongPath_notOk() {
        writer = new CsvFileWriterImpl("src/test/java/resour/re.csv");

        assertThrows(RuntimeException.class, () -> writer.writing("apple"));
    }
}
