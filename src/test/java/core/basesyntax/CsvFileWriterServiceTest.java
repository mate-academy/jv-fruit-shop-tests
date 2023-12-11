package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.PathDoesNotExistException;
import core.basesyntax.exceptions.WrongExtensionException;
import core.basesyntax.service.CsvFileReaderService;
import core.basesyntax.service.CsvFileWriterService;
import core.basesyntax.service.impl.CsvFileWriterServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvFileWriterServiceTest {
    private static CsvFileReaderService csvFileReaderService;
    private static final String VALID_DATA = """
                fruit,quantity
                banana,123
                apple,909
                coconut,90""";
    private static final String INVALID_PATH = "shos/neponiatne";
    private static final String INVALID_EXTENSION = "src/test/resources/notCsvFile.txt";
    private static final String REPORT_PATH = "src/test/resources/report.csv";
    private static CsvFileWriterService csvFileWriterService;

    @BeforeAll
    public static void setCsvFileWriterService() {
        csvFileWriterService = new CsvFileWriterServiceImpl();
    }

    @Test
    public void writeToFile_invalidPath_NotOk() {
        assertThrows(PathDoesNotExistException.class,
                () -> csvFileWriterService.writeToFile(VALID_DATA, INVALID_PATH),
                "File path doesn't exist!");
    }

    @Test
    public void writeToFile_validPath_Ok() {
        assertDoesNotThrow(() -> csvFileWriterService.writeToFile(VALID_DATA, REPORT_PATH),
                "File path doesn't exist!");
    }

    @Test
    public void writeToFile_invalidExtension_NotOk() {
        assertThrows(WrongExtensionException.class,
                () -> csvFileWriterService.writeToFile(VALID_DATA, INVALID_EXTENSION),
                "You can only use csv files!");
    }

    @Test
    public void writeToFile_validExtension_Ok() {
        assertDoesNotThrow(() -> csvFileWriterService.writeToFile(VALID_DATA, REPORT_PATH),
                "You can only use csv files!");
    }

}
