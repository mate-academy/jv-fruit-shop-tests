package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.ReadFileException;
import core.basesyntax.service.ReadCsvFileService;
import core.basesyntax.service.implementations.ReadCsvFileServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReadCsvFileServiceTest {
    private static String SOURCE_FILE = "src/test/resources/NormalTestInput.csv";
    private static ReadCsvFileService readCsvFileService;

    @BeforeEach
    void setUp() {
        readCsvFileService = new ReadCsvFileServiceImpl();
    }

    @Test
    void normalFileReadOkay() {
        assertDoesNotThrow(() -> readCsvFileService.readFile(SOURCE_FILE));
    }

    @Test
    void testReadFileFileNotExistNotOkay() {
        SOURCE_FILE = "nonexistent-file.csv";
        assertThrows(ReadFileException.class, () -> readCsvFileService.readFile(SOURCE_FILE));
    }

    @AfterEach
    void onTearDown() {
        SOURCE_FILE = "src/test/resources/NormalTestInput.csv";
    }
}
