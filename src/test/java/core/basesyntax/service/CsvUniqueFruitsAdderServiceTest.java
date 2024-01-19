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
    private static final String VALID_FILE_PATH = "src/test/resources/input.csv";
    private static final String NON_VALID_STRUCTURE_FILE_PATH =
            "src/test/resources/non-validStructureInTransactions.csv";
    private static final String NON_VALID_STRATEGY_FILE_PATH =
            "src/test/resources/nonValidStrategyFile.csv";
    private static final String NON_VALID_STRUCTURE_IN_TRANSACTION_FILE_PATH =
            "src/test/resources/non-validStructureInTransactions.csv";
    private static final String NON_VALID_QUANTITY_FILE_PATH =
            "src/test/resources/non-validQuantity.csv";
    private Storage storage = new Storage();
    private UniqueFruitsAdderService uniqueFruitsAdderService
            = new CsvFileUniqueFruitsServiceAdderImpl(storage);
    private FileReaderService fileReaderService = new CsvFileReaderServiceImpl();

    @Test
    void add_validMapAfterAdding_Ok() {
        uniqueFruitsAdderService.add(
                fileReaderService.readFile(new File(VALID_FILE_PATH)),
                storage);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 0);
        expected.put("apple",0);
        assertEquals(expected, storage.getFruitQuantityMap());
    }

    @Test
    void add_nonValidFile_notOk() {
        assertThrows(IllegalArgumentException.class, () -> uniqueFruitsAdderService.add(
                fileReaderService.readFile(new File(NON_VALID_STRUCTURE_FILE_PATH)),
                storage));
    }

    @Test
    void add_nonValidStrategy_notOk() {
        List<String> readFile =
                new CsvFileReaderServiceImpl().readFile(new File(NON_VALID_STRATEGY_FILE_PATH));
        assertThrows(IllegalArgumentException.class,
                () -> new CsvFileUniqueFruitsServiceAdderImpl(storage).add(readFile, storage));
    }

    @Test
    void add_nonValidTransactionStructure_notOk() {
        List<String> readFile =
                new CsvFileReaderServiceImpl()
                        .readFile(new File(NON_VALID_STRUCTURE_IN_TRANSACTION_FILE_PATH));
        assertThrows(IllegalArgumentException.class,
                () -> new CsvFileUniqueFruitsServiceAdderImpl(storage).add(readFile, storage));
    }

    @Test
    void add_nonValidQuantity_notOk() {
        List<String> readFile =
                new CsvFileReaderServiceImpl()
                        .readFile(new File(NON_VALID_QUANTITY_FILE_PATH));
        assertThrows(IllegalArgumentException.class,
                () -> new CsvFileUniqueFruitsServiceAdderImpl(storage).add(readFile, storage));
    }
}
