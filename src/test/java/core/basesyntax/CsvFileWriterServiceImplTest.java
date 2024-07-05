package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.CsvFileWriterServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CsvFileWriterServiceImplTest {

    private final CsvFileWriterServiceImpl writerServise = new CsvFileWriterServiceImpl();

    @Test
    void writeToFile_valideFile() {
        try {
            Path tempPath = File.createTempFile("test", ".csv").toPath();
            writerServise.writeToFile(tempPath.toString(),
                    List.of("head", "firstLine", "secondLine"));

            List<String> readedLines = Files.readAllLines(tempPath);

            assertEquals(3, readedLines.size());
            assertTrue(readedLines.contains("head"));
            assertTrue(readedLines.contains("firstLine"));
            assertTrue(readedLines.contains("secondLine"));

            Files.deleteIfExists(tempPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void writeToFile_invalidFile() {
        assertThrows(RuntimeException.class, ()
                -> writerServise.writeToFile("/non-exist/file.path",
                List.of("head")));
    }

    @Test
    void writeToFile_invalidLines() {
        try {
            Path tempPath = File.createTempFile("test", ".csv").toPath();
            assertThrows(RuntimeException.class, ()
                    -> writerServise.writeToFile(tempPath.toString(), null));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
