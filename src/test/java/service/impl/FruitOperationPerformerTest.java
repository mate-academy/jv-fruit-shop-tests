package service.impl;

import db.FruitStorage;
import db.FruitStorageDao;
import db.StorageDao;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Performer;
import strategy.FruitTransactionStrategy;
import strategy.TransactionStrategy;
import strategy.operation.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class FruitOperationPerformerTest {
    public static final FruitStorage FRUIT_STORAGE = new FruitStorage();
    public static final StorageDao<String,Integer> STORAGE_HANDLER = new FruitStorageDao(FRUIT_STORAGE);
    public static final Map<FruitTransaction.Operation,
            OperationHandler<String, Integer>> operationHandlerMap = Map.of(
            FruitTransaction.Operation.BALANCE,
            new FruitBalanceOperation(STORAGE_HANDLER),
            FruitTransaction.Operation.PURCHASE,
            new FruitPurchaseOperation(STORAGE_HANDLER),
            FruitTransaction.Operation.SUPPLY,
            new FruitSupplyOperation(STORAGE_HANDLER),
            FruitTransaction.Operation.RETURN,
            new FruitReturnOperation(STORAGE_HANDLER)
    );

    @Test
    void performProceses_Ok() {
        List<FruitTransaction> fruitTransactionsTest = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 60),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 90),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 15));
        FruitTransaction testTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 60);
        TransactionStrategy makeTransaction = new FruitTransactionStrategy(operationHandlerMap);
        Performer performer = new FruitOperationPerformer(makeTransaction);
        performer.performProcesses(fruitTransactionsTest);

        assertEquals(FRUIT_STORAGE.getFruits().get("banana"), 135);
    }

    @Test
    void performProceses_NotOk() {
        TransactionStrategy makeTransaction = new FruitTransactionStrategy(operationHandlerMap);
        Performer performer = new FruitOperationPerformer(makeTransaction);
        assertFalse(performer.performProcesses(null));
        assertFalse(performer.performProcesses(List.of()));
    }
}