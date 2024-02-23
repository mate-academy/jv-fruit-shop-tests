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
    private static FruitStorage FRUIT_STORAGE;
    private static StorageDao<String,Integer> STORAGE_HANDLER;
    private static Map<FruitTransaction.Operation,
            OperationHandler<String, Integer>> OPERATION_HANDLER_MAP;

    @BeforeEach
    void setUp() {
        FRUIT_STORAGE = new FruitStorage();
        STORAGE_HANDLER = new FruitStorageDao(FRUIT_STORAGE);
        OPERATION_HANDLER_MAP = Map.of(
                FruitTransaction.Operation.BALANCE,
                new FruitBalanceOperation(STORAGE_HANDLER),
                FruitTransaction.Operation.PURCHASE,
                new FruitPurchaseOperation(STORAGE_HANDLER),
                FruitTransaction.Operation.SUPPLY,
                new FruitSupplyOperation(STORAGE_HANDLER),
                FruitTransaction.Operation.RETURN,
                new FruitReturnOperation(STORAGE_HANDLER)
        );
    }

    @Test
    void getReport_Empty_Ok() {
        Reporter fruitReporter = new FruitReporter(FRUIT_STORAGE);
        String expected = REPORT_HEAD;
        String actual = fruitReporter.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_FruitsStorageAfterOperations_Ok() {
        TransactionStrategy maketransaction = new FruitTransactionStrategy(OPERATION_HANDLER_MAP);
        Performer performer = new FruitOperationPerformer(maketransaction);
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 30),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 15),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 5)
        );
        performer.performProcesses(transactions);

        Reporter reporter = new FruitReporter(FRUIT_STORAGE);
        String actual = reporter.getReport();
        String expected = TEST_REPORT;
        assertEquals(expected, actual);
    }
}
