package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.ReadFileException;
import core.basesyntax.service.ReadCsvFileService;
import core.basesyntax.service.implementations.ReadCsvFileServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReadCsvFileServiceTest {
    private static String SOURCE_FILE = "src/test/resources/NormalTestInput.csv";
    private static String NOT_EXIST_SOURCE_FILE = "src/test/resources/nonexistent-file.csv";
    private static ReadCsvFileService readCsvFileService;

    @BeforeAll
    static void setReadService() {
        readCsvFileService = new ReadCsvFileServiceImpl();
    }

    @Test
    void readFile_validData_okay() {
        List<String> data = assertDoesNotThrow(() -> readCsvFileService.readFile(SOURCE_FILE));
        try {
            List<String> expectedData = Files.readAllLines(Path.of(SOURCE_FILE));
            assertEquals(data, expectedData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void readFile_validFileName_okay() {
        assertDoesNotThrow(() -> readCsvFileService.readFile(SOURCE_FILE));
    }

    @Test
    void readFile_missingFile_notOkay() {
        assertThrows(ReadFileException.class,
                () -> readCsvFileService.readFile(NOT_EXIST_SOURCE_FILE));
    }

    @Test
    void readFile_nullFile_notOkay() {
        assertThrows(ReadFileException.class,
                () -> readCsvFileService.readFile(null));
    }
}
