package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorTest {

    private Map<OperationType, OperationHandler> operationStrategy;
    private TransactionProcessor transactionProcessor;

    @BeforeEach
    void setUp() {
        operationStrategy = new HashMap<>();
        transactionProcessor = new TransactionProcessor(operationStrategy);
        operationStrategy.put(OperationType.SUPPLY, new SupplyOperationHandler());
        operationStrategy.put(OperationType.PURCHASE, new PurchaseOperationHandler());
        operationStrategy.put(OperationType.BALANCE, new BalanceOperationHandler());
        operationStrategy.put(OperationType.RETURN, new ReturnOperationHandler());
    }

    @Test
    void applyOperation_validBalanceOperation_updatesInventory() {
        FruitTransaction transaction = new FruitTransaction(OperationType.BALANCE, "banana", 20);
        transactionProcessor.applyOperation(transaction);
        assertEquals(20, Storage.inventory.get("banana"));
    }

    @Test
    void applyOperation_validSupplyOperation_updatesInventory() {
        FruitTransaction transaction = new FruitTransaction(OperationType.SUPPLY, "banana", 10);
        transactionProcessor.applyOperation(transaction);
        assertEquals(10, Storage.inventory.get("banana"));
    }

    @Test
    void applyOperation_validReturnOperation_updatesInventory() {
        Storage.inventory.put("banana", 10);
        FruitTransaction transaction = new FruitTransaction(OperationType.RETURN, "banana", 5);
        transactionProcessor.applyOperation(transaction);
        assertEquals(15, Storage.inventory.get("banana"));
    }

    @Test
    void applyOperation_validPurchaseOperation_updatesInventory() {
        Storage.inventory.put("banana", 10);
        FruitTransaction transaction = new FruitTransaction(OperationType.PURCHASE, "banana", 5);
        transactionProcessor.applyOperation(transaction);
        assertEquals(5, Storage.inventory.get("banana"));
    }
}
