package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ReaderServiceImplTest {
    private final ReaderService readerService = new ReaderServiceImpl();

    @Test
    void read_validFile_notOk() throws IOException {
        Path tempFile = Files.createTempFile("test", ".txt");
        List<String> result = readerService.read(tempFile.toString());
        assertNotNull(result, "Result should not be null");
    }

    @Test
    void read_nonExistentFile_notOk() {
        String nonExistentFileName = "non_existed.txt";
        assertThrows(RuntimeException.class, () -> readerService.read(nonExistentFileName),
                "non existent file");
    }

    @Test
    void read_emptyFile_notOk() throws IOException {
        Path emptyFile = Files.createTempFile("empty", ".csv");
        List<String> result = readerService.read(emptyFile.toString());
        assertNotNull(result, "Result should not be null");
        assertEquals(0, result.size(), "Result should be an empty list");
    }
}
