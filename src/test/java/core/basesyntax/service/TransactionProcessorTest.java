package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @BeforeEach
    void setUp() {
        operationStrategy = new HashMap<>();
        operationStrategy.put(OperationType.SUPPLY, new SupplyOperationHandler());
        operationStrategy.put(OperationType.PURCHASE, new PurchaseOperationHandler());
        operationStrategy.put(OperationType.BALANCE, new BalanceOperationHandler());
        operationStrategy.put(OperationType.RETURN, new ReturnOperationHandler());
    }

    @Test
    void applyOperation_validBalanceOperation_updatesInventory() {
        TransactionProcessor.applyOperation(OperationType.BALANCE, "banana",
                20, operationStrategy);
        assertEquals(20, Storage.inventory.get("banana"));
    }

    @Test
    void applyOperation_validSupplyOperation_updatesInventory() {
        TransactionProcessor.applyOperation(OperationType.SUPPLY, "banana",
                10, operationStrategy);
        assertEquals(10, Storage.inventory.get("banana"));
    }

    @Test
    void applyOperation_validReturnOperation_updatesInventory() {
        Storage.inventory.put("banana", 10);
        TransactionProcessor.applyOperation(OperationType.RETURN, "banana",
                5, operationStrategy);
        assertEquals(15, Storage.inventory.get("banana"));
    }

    @Test
    void applyOperation_validPurchaseOperation_updatesInventory() {
        Storage.inventory.put("banana", 10);
        TransactionProcessor.applyOperation(OperationType.PURCHASE, "banana",
                5, operationStrategy);
        assertEquals(5, Storage.inventory.get("banana"));
    }
}
