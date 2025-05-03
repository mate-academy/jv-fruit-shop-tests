package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.FruitStorage;
import db.FruitStorageDao;
import db.StorageDao;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Performer;
import strategy.FruitTransactionStrategy;
import strategy.TransactionStrategy;
import strategy.operation.FruitBalanceOperation;
import strategy.operation.FruitPurchaseOperation;
import strategy.operation.FruitReturnOperation;
import strategy.operation.FruitSupplyOperation;
import strategy.operation.OperationHandler;

class FruitOperationPerformerTest {
    private static FruitStorage fruitStorage;
    private static StorageDao<String,Integer> storageHandler;
    private static Map<FruitTransaction.Operation,
            OperationHandler<String, Integer>> operationOperationHandlerMap;
    private static TransactionStrategy makeTransaction;

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
        makeTransaction = new FruitTransactionStrategy(operationOperationHandlerMap);
    }

    @Test
    void performProceses_ValidListTransactions_Ok() {
        List<FruitTransaction> fruitTransactionsTest = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 60),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 90),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 15));
        TransactionStrategy makeTransaction =
                new FruitTransactionStrategy(operationOperationHandlerMap);
        Performer performer = new FruitOperationPerformer(makeTransaction);
        performer.performProcesses(fruitTransactionsTest);

        int actual = fruitStorage.getFruits().get("banana");
        int expected = 60 + 90 - 30 + 15;

        assertEquals(expected, actual);
    }

    @Test
    void performProceses_Null_NotOk() {
        Performer performer = new FruitOperationPerformer(makeTransaction);
        assertFalse(performer.performProcesses(null));
    }

    @Test
    void performProceses_EmptyList_NotOk() {
        Performer performer = new FruitOperationPerformer(makeTransaction);
        assertFalse(performer.performProcesses(List.of()));
    }

    @Test
    void performProceses_FruitsCountNotEnoughToPerform_NotOk() {
        Performer performer = new FruitOperationPerformer(makeTransaction);
        List<FruitTransaction> fruitTransactionsTest = List.of(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 30));
        assertThrows(RuntimeException.class, () -> {
            performer.performProcesses(fruitTransactionsTest);
        });
    }
}
