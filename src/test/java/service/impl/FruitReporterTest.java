package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.FruitStorage;
import db.FruitStorageDao;
import db.StorageDao;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Performer;
import service.Reporter;
import strategy.FruitTransactionStrategy;
import strategy.TransactionStrategy;
import strategy.operation.FruitBalanceOperation;
import strategy.operation.FruitPurchaseOperation;
import strategy.operation.FruitReturnOperation;
import strategy.operation.FruitSupplyOperation;
import strategy.operation.OperationHandler;

class FruitReporterTest {
    private static final String REPORT_HEAD = "fruit,quantity";
    private static final String TEST_REPORT = "fruit,quantity"
            + System.lineSeparator() + "banana,120";
    private static FruitStorage fruitStorage;
    private static StorageDao<String,Integer> storageHandler;
    private static Map<FruitTransaction.Operation,
            OperationHandler<String, Integer>> operationOperationHandlerMap;

    @BeforeEach
    void setUp() {
        fruitStorage = new FruitStorage();
        storageHandler = new FruitStorageDao(fruitStorage);
        operationOperationHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE,
                new FruitBalanceOperation(storageHandler),
                FruitTransaction.Operation.PURCHASE,
                new FruitPurchaseOperation(storageHandler),
                FruitTransaction.Operation.SUPPLY,
                new FruitSupplyOperation(storageHandler),
                FruitTransaction.Operation.RETURN,
                new FruitReturnOperation(storageHandler)
        );
    }

    @Test
    void getReport_Empty_Ok() {
        Reporter fruitReporter = new FruitReporter(fruitStorage);
        String expected = REPORT_HEAD;
        String actual = fruitReporter.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_FruitsStorageAfterOperations_Ok() {
        TransactionStrategy maketransaction =
                new FruitTransactionStrategy(operationOperationHandlerMap);
        Performer performer = new FruitOperationPerformer(maketransaction);
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 30),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 15),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 5)
        );
        performer.performProcesses(transactions);

        Reporter reporter = new FruitReporter(fruitStorage);
        String actual = reporter.getReport();
        String expected = TEST_REPORT;
        assertEquals(expected, actual);
    }
}
