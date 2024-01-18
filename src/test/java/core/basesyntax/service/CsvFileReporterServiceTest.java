package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import core.basesyntax.service.impl.CsvFileReporterServiceImpl;
import core.basesyntax.service.impl.CsvFileUniqueFruitsServiceAdderImpl;
import core.basesyntax.service.impl.OperationStrategyServiceImpl;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class CsvFileReporterServiceTest {
    private static final String VALID_FILE_PATH = "src/main/resources/input.csv";
    private static final Map<FruitTransaction.Operation, OperationHandler> operationProviderMap =
            Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                    FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(),
                    FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                    FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
    private OperationStrategyService operationServiceStrategy =
            new OperationStrategyServiceImpl(operationProviderMap);
    private Storage storage = new Storage();
    private FileReporterService fileReporterService =
            new CsvFileReporterServiceImpl(operationServiceStrategy, storage);

    @AfterEach
    void tearDown() {
        storage = new Storage();
    }

    @Test
    void getRightReport() {
        List<String> readFile =
                new CsvFileReaderServiceImpl().readFile(new File(VALID_FILE_PATH));
        new CsvFileUniqueFruitsServiceAdderImpl(storage).add(readFile, storage);
        fileReporterService.getReport(readFile);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 152);
        expected.put("apple", 90);
        Map<String, Integer> actual = storage.getFruitQuantityMap();
        assertEquals(expected, actual);
    }
}
