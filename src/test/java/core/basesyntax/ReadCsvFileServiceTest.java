package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.ReadFileException;
import core.basesyntax.service.ReadCsvFileService;
import core.basesyntax.service.implementations.ReadCsvFileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReadCsvFileServiceTest {
    private static String SOURCE_FILE = "src/test/resources/NormalTestInput.csv";
    private static String NOT_EXIST_SOURCE_FILE = "src/test/resources/nonexistent-file.csv";
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
        assertThrows(ReadFileException.class,
                () -> readCsvFileService.readFile(NOT_EXIST_SOURCE_FILE));
    }

    @Test
    void testReadNullFileNotOkay() {
        assertThrows(ReadFileException.class,
                () -> readCsvFileService.readFile(null));
    }
}
