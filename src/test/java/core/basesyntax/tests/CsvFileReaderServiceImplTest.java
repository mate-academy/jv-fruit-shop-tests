package core.basesyntax.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CsvFileReaderService;
import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvFileReaderServiceImplTest {
    private static final String VALID_DATA_FILE_PATH =
            "src/main/java/core/basesyntax/inputFiles/validInputFile.csv";
    private static final String EMPTY_FILE_PATH =
            "src/main/java/core/basesyntax/inputFiles/emptyInputFile.csv";
    private static final String INVALID_DATA_FILE_PATH =
            "src/main/java/core/basesyntax/invalidInputFile.csv";
    private CsvFileReaderService fileReaderService;

    @BeforeEach
    public void setUp() {
        fileReaderService = new CsvFileReaderServiceImpl();
    }

    @Test
    public void readFromFile_validData_Ok() {
        List<FruitTransaction> transactions = fileReaderService.readFromFile(VALID_DATA_FILE_PATH);
        assertNotNull(transactions);
        assertEquals(8, transactions.size());
    }

    @Test
    public void readFromFile_emptyFile_NotOk() {
        List<FruitTransaction> transactions = fileReaderService.readFromFile(EMPTY_FILE_PATH);
        assertNotNull(transactions);
        assertEquals(0, transactions.size());
    }

    @Test
    public void readFromFile_invalidData_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fileReaderService.readFromFile(INVALID_DATA_FILE_PATH));
    }
}



