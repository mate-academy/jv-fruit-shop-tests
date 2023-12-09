package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.PathDoesNotExistException;
import core.basesyntax.exceptions.WrongExtensionException;
import core.basesyntax.service.CsvFileWriterService;
import core.basesyntax.service.impl.CsvFileWriterServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvFileWriterServiceTest {
    private static CsvFileWriterService csvFileWriterService;

    @BeforeAll
    public static void setCsvFileWriterService() {
        csvFileWriterService = new CsvFileWriterServiceImpl();
    }

    @Test
    public void writeToFile_invalidPath_NotOk() {
        String validData = """
                fruit,quantity
                banana,123
                apple,909
                coconut,90""";
        String path = "shos\\neponiatne";
        assertThrows(PathDoesNotExistException.class,
                () -> csvFileWriterService.writeToFile(validData, path),
                "File path doesn't exist!");
    }

    @Test
    public void writeToFile_validPath_Ok() {
        String validData = """
                fruit,quantity
                banana,123
                apple,909
                coconut,90""";
        String path = "src\\test\\resources\\report.csv";
        assertDoesNotThrow(() -> csvFileWriterService.writeToFile(validData, path),
                "File path doesn't exist!");
    }

    @Test
    public void writeToFile_invalidExtension_NotOk() {
        String validData = """
                fruit,quantity
                banana,123
                apple,909
                coconut,90""";
        String path = "src\\test\\resources\\notCsvFile.txt";
        assertThrows(WrongExtensionException.class,
                () -> csvFileWriterService.writeToFile(validData, path),
                "You can only use csv files!");
    }

    @Test
    public void writeToFile_validExtension_Ok() {
        String validData = """
                fruit,quantity
                banana,123
                apple,909
                coconut,90""";
        String path = "src\\test\\resources\\report.csv";
        assertDoesNotThrow(() -> csvFileWriterService.writeToFile(validData, path),
                "You can only use csv files!");
    }

}
