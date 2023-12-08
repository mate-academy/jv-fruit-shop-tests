package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.exceptions.PathDoesNotExistException;
import core.basesyntax.exceptions.WrongExtensionException;
import core.basesyntax.service.CsvFileReaderService;
import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvFileReaderServiceTest {
    private static CsvFileReaderService csvFileReaderService;

    @BeforeAll
    public static void setCsvFileReaderService() {
        csvFileReaderService = new CsvFileReaderServiceImpl();
    }

    @Test
    public void readFromFile_invalidPath_NotOk() {
        String path = "shos\\neponiatne";
        assertThrows(PathDoesNotExistException.class,
                () -> csvFileReaderService.readFromFile(path),
                "File path doesn't exist!");
    }

    @Test
    public void readFromFile_validPath_Ok() {
        String path = "src\\test\\resources\\products.csv";
        assertDoesNotThrow(() -> csvFileReaderService.readFromFile(path),
                "File path doesn't exist!");
    }

    @Test
    public void readFromFile_invalidExtension_NotOk() {
        String path = "src\\test\\resources\\notCsvFile.txt";
        assertThrows(WrongExtensionException.class,
                () -> csvFileReaderService.readFromFile(path),
                "You can only use csv files!");
    }

    @Test
    public void readFromFile_validExtension_Ok() {
        String path = "src\\test\\resources\\products.csv";
        assertDoesNotThrow(() -> csvFileReaderService.readFromFile(path),
                "You can only use csv files!");
    }

    @Test
    public void readFromFile_invalidData_NotOk() {
        String path = "src\\test\\resources\\invalidColumnsNumberFile.csv";
        assertThrows(InvalidDataException.class,
                () -> csvFileReaderService.readFromFile(path),
                "Csv file should have 3 columns!");
    }

    @Test
    public void readFromFile_validData_Ok() {
        String path = "src\\test\\resources\\products.csv";
        assertDoesNotThrow(() -> csvFileReaderService.readFromFile(path),
                "Csv file should have 3 columns!");
    }
}
