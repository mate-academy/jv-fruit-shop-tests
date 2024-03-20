package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String INVALID_PATH_TO_FILE = "src/file.csv";
    private static final String VALID_PATH_TO_FILE = "src/test/resources/fileToRead.csv";
    private ReaderService readerService;

    @BeforeEach
    public void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_fileNotFound_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(INVALID_PATH_TO_FILE));
        assertEquals("Can't find file with path" + INVALID_PATH_TO_FILE, exception.getMessage());
    }

    @Test
    void readFrom_fileExist_ok() throws IOException {
        List<String> expected = new ArrayList<>();
        Files.write(Path.of(VALID_PATH_TO_FILE),expected);
        List<String> actualResult = readerService.readFromFile(VALID_PATH_TO_FILE);
        assertEquals(expected, actualResult);
    }
}
