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
    private static final String HEAD = "head";
    private static final String FIRST_LINE = "firstLine";
    private static final String SECOND_LINE = "secondLine";
    private final CsvFileWriterServiceImpl writerServise = new CsvFileWriterServiceImpl();

    @Test
    void writeToFile_valideFile_ok() throws IOException {
        Path tempPath = File.createTempFile("test", ".csv").toPath();
        writerServise.writeToFile(tempPath.toString(),
                List.of(HEAD, FIRST_LINE, SECOND_LINE));

        List<String> readedLines = Files.readAllLines(tempPath);

        assertEquals(3, readedLines.size());
        assertTrue(readedLines.contains(HEAD));
        assertTrue(readedLines.contains(FIRST_LINE));
        assertTrue(readedLines.contains(SECOND_LINE));

        Files.deleteIfExists(tempPath);
    }

    @Test
    void writeToFile_invalidFil_throwsException() {
        assertThrows(RuntimeException.class, ()
                -> writerServise.writeToFile("/non-exist/file.path",
                List.of(HEAD)));
    }

    @Test
    void writeToFile_invalidLines_throwsException() throws IOException {
        Path tempPath = File.createTempFile("test", ".csv").toPath();
        assertThrows(RuntimeException.class, ()
                -> writerServise.writeToFile(tempPath.toString(), null));
    }
}
