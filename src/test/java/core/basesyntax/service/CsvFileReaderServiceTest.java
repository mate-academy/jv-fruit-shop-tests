package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.exceptions.PathDoesNotExistException;
import core.basesyntax.exceptions.WrongExtensionException;
import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvFileReaderServiceTest {
    private static CsvFileReaderService csvFileReaderService;
    private static final String INVALID_PATH = "shos/neponiatne";
    private static final String VALID_FILE = "src/test/resources/products.csv";
    private static final String INVALID_EXTENSION = "src/test/resources/notCsvFile.txt";
    private static final String INVALID_DATA = "src/test/resources/invalidColumnsNumberFile.csv";

    @BeforeAll
    public static void init() {
        csvFileReaderService = new CsvFileReaderServiceImpl();
    }

    @Test
    public void readFromFile_invalidPath_notOk() {
        assertThrows(PathDoesNotExistException.class,
                () -> csvFileReaderService.readFromFile(INVALID_PATH),
                "File path doesn't exist!");
    }

    @Test
    public void readFromFile_validPath_ok() {
        assertDoesNotThrow(() -> csvFileReaderService.readFromFile(VALID_FILE),
                "File path doesn't exist!");
    }

    @Test
    public void readFromFile_invalidExtension_notOk() {
        assertThrows(WrongExtensionException.class,
                () -> csvFileReaderService.readFromFile(INVALID_EXTENSION),
                "You can only use csv files!");
    }

    @Test
    public void readFromFile_validExtension_ok() {
        assertDoesNotThrow(() -> csvFileReaderService.readFromFile(VALID_FILE),
                "You can only use csv files!");
    }

    @Test
    public void readFromFile_invalidData_notOk() {
        assertThrows(InvalidDataException.class,
                () -> csvFileReaderService.readFromFile(INVALID_DATA),
                "Csv file should have 3 columns!");
    }

    @Test
    public void readFromFile_validData_ok() {
        assertDoesNotThrow(() -> csvFileReaderService.readFromFile(VALID_FILE),
                "Csv file should have 3 columns!");
    }
}
