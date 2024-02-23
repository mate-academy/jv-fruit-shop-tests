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
    private static FruitStorage FRUIT_STORAGE;
    private static StorageDao<String,Integer> STORAGE_HANDLER;
    private static Map<FruitTransaction.Operation,
            OperationHandler<String, Integer>> OPERATION_HANDLER_MAP;
    private static TransactionStrategy MAKE_TRANSACTION;

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
        MAKE_TRANSACTION = new FruitTransactionStrategy(OPERATION_HANDLER_MAP);
    }

    @Test
    void performProceses_ValidListTransactions_Ok() {
        List<FruitTransaction> fruitTransactionsTest = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 60),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 90),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 15));
        TransactionStrategy makeTransaction = new FruitTransactionStrategy(OPERATION_HANDLER_MAP);
        Performer performer = new FruitOperationPerformer(makeTransaction);
        performer.performProcesses(fruitTransactionsTest);

        int actual = FRUIT_STORAGE.getFruits().get("banana");
        int expected = 60 + 90 - 30 + 15;

        assertEquals(expected, actual);
    }

    @Test
    void performProceses_Null_NotOk() {
        Performer performer = new FruitOperationPerformer(MAKE_TRANSACTION);
        assertFalse(performer.performProcesses(null));
    }

    @Test
    void performProceses_EmptyList_NotOk() {
        Performer performer = new FruitOperationPerformer(MAKE_TRANSACTION);
        assertFalse(performer.performProcesses(List.of()));
    }

    @Test
    void performProceses_FruitsCountNotEnoughToPerform_NotOk() {
        Performer performer = new FruitOperationPerformer(MAKE_TRANSACTION);
        List<FruitTransaction> fruitTransactionsTest = List.of(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 30));
        assertThrows(RuntimeException.class, () -> {
            performer.performProcesses(fruitTransactionsTest);
        });
    }
}
