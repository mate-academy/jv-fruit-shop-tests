package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import service.ReaderService;

public class ReaderServiceImplTest {

    private final ReaderService readerService = new ReaderServiceImpl();

    @Test
    void readFromFile_validFile_returnsAllLines() throws IOException {
        Path path = Path.of("src", "test", "resources", "test-data.csv");
        List<String> expectedLines = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,15"
        );

        List<String> actualLines = readerService.readFromFile(path.toString());

        assertEquals(expectedLines, actualLines, "The file lines should match expected content");
    }

    @Test
    void readFromFile_fileDoesNotExist_throwsRuntimeException() {
        String nonExistentPath = "not_existing_file.csv";
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> readerService.readFromFile(nonExistentPath)
        );
        assertTrue(exception.getMessage().contains("Can't read file by path"));
    }
}
