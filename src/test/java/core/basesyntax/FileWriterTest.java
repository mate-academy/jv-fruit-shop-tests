package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileWriterTest {

    private static FileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_validData_ok(@TempDir Path tempDir) throws IOException {
        Path filePath = tempDir.resolve("test-report.csv");
        String reportContent = "fruit,quantity\nbanana,10";

        fileWriter.write(reportContent, filePath.toString());

        assertTrue(Files.exists(filePath));
        assertEquals(reportContent, Files.readString(filePath));
    }

    @Test
    void write_filePathIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileWriter.write("some report", null);
        });
    }

    @Test
    void write_wrongFilePath_notOk() {
        String path = "src/test/resources";
        String content = "fruit,quantity\npineapple,2";

        assertThrows(RuntimeException.class, () -> {
            fileWriter.write(content, path);
        });
    }
}
