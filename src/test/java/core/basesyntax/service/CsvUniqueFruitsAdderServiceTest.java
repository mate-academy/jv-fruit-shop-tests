package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import core.basesyntax.service.impl.CsvFileUniqueFruitsServiceAdderImpl;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CsvUniqueFruitsAdderServiceTest {
    private static final String VALID_FILE_PATH = "src/main/resources/input.csv";
    private static final String NON_VALID_STRUCTURE_FILE_PATH =
            "src/main/resources/non-validStructureInTransactions.csv";
    private static final String NON_VALID_STRATEGY_FILE_PATH =
            "src/main/resources/nonValidStrategyFile.csv";
    private static final String NON_VALID_STRUCTURE_IN_TRANSACTION_FILE_PATH =
            "src/main/resources/non-validStructureInTransactions.csv";
    private static final String NON_VALID_QUANTITY_FILE_PATH =
            "src/main/resources/non-validQuantity.csv";
    private Storage storage = new Storage();
    private UniqueFruitsAdderService uniqueFruitsAdderService
            = new CsvFileUniqueFruitsServiceAdderImpl(storage);
    private FileReaderService fileReaderService = new CsvFileReaderServiceImpl();

    @Test
    void validSolutionTest() {
        uniqueFruitsAdderService.add(
                fileReaderService.readFile(new File(VALID_FILE_PATH)),
                storage);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 0);
        expected.put("apple",0);
        assertEquals(expected, storage.getFruitQuantityMap());
    }

    @Test
    void nonValidStructure() {
        assertThrows(IllegalArgumentException.class, () -> uniqueFruitsAdderService.add(
                fileReaderService.readFile(new File(NON_VALID_STRUCTURE_FILE_PATH)),
                storage));
    }

    @Test
    void nonValidStrategy_notOk() {
        List<String> readFile =
                new CsvFileReaderServiceImpl().readFile(new File(NON_VALID_STRATEGY_FILE_PATH));
        assertThrows(IllegalArgumentException.class,
                () -> new CsvFileUniqueFruitsServiceAdderImpl(storage).add(readFile, storage));
    }

    @Test
    void nonValidTransactionStructure_notOk() {
        List<String> readFile =
                new CsvFileReaderServiceImpl()
                        .readFile(new File(NON_VALID_STRUCTURE_IN_TRANSACTION_FILE_PATH));
        assertThrows(IllegalArgumentException.class,
                () -> new CsvFileUniqueFruitsServiceAdderImpl(storage).add(readFile, storage));
    }

    @Test
    void nonValidQuantity_notOk() {
        List<String> readFile =
                new CsvFileReaderServiceImpl()
                        .readFile(new File(NON_VALID_QUANTITY_FILE_PATH));
        assertThrows(IllegalArgumentException.class,
                () -> new CsvFileUniqueFruitsServiceAdderImpl(storage).add(readFile, storage));
    }
}
