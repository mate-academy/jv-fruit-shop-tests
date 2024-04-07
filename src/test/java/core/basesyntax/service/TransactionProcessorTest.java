package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionProcessorTest {
    private TransactionProcessor transactionProcessor;
    private Storage storage;
    private HashMap<FruitTransaction.Operation, OperationStrategy> operationMap;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        operationMap = new HashMap<>();
        operationMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        transactionProcessor = new TransactionProcessor(storage, operationMap);
    }

    @Test
    void processTransaction_BalanceOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10);
        transactionProcessor.processTransaction(transaction);
        assertEquals(storage.getFruitCount(transaction.getFruit()), transaction.getQuantity());
    }

    @Test
    void processTransaction_PurchaseOperation_Ok() {
        storage.setFruits("orange",100);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "orange", 10);
        transactionProcessor.processTransaction(transaction);
        int expected = 90;
        int actual = storage.getFruitCount(transaction.getFruit());
        assertEquals(expected, actual);
    }

    @Test
    void processTransaction_PurchaseOperation_NotOk() {
        storage.setFruits("orange",10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "orange", 100);
        assertThrows(IllegalArgumentException.class, () -> {
            transactionProcessor.processTransaction(transaction);
        });
    }

    @Test
    void processTransaction_ReturnOperation_Ok() {
        storage.setFruits("apple",15);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 10);
        transactionProcessor.processTransaction(transaction);
        int expected = 25;
        int actual = storage.getFruitCount(transaction.getFruit());
        assertEquals(expected, actual);
    }

    @Test
    void processTransaction_SupplyOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "peach", 3);
        transactionProcessor.processTransaction(transaction);
        System.out.println(storage.getFruitCount(transaction.getFruit()));
    }
}
