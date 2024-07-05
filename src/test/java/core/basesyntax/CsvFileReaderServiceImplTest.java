package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CsvFileReaderServiceImplTest {

    private final CsvFileReaderServiceImpl readerService = new CsvFileReaderServiceImpl();

    @Test
    void readFromFile_valideFile() {
        try {
            Path tempPath = File.createTempFile("test", ".csv").toPath();
            Files.write(tempPath, List.of("fruit_name", "quantity", "quality"));

            List<String> readedLines = readerService.readFromFile(tempPath.toString());
            assertEquals(2, readedLines.size());
            assertTrue(readedLines.contains("quantity"));
            assertTrue(readedLines.contains("quality"));

            Files.deleteIfExists(tempPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void readFromFile_invalidFile() {
        assertThrows(RuntimeException.class, () ->
                readerService.readFromFile("non-existed.file"));
    }
}
