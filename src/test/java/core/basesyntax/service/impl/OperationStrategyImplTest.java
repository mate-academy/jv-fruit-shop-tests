package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.Storage;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;
    private FruitStorage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();

        Map<FruitTransaction.Operation, OperationHandler>
                operationHandlers = new EnumMap<>(FruitTransaction.Operation.class);
        operationHandlers.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperation(storage));
        operationHandlers.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperation(storage));
        operationHandlers.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperation(storage));
        operationHandlers.put(FruitTransaction.Operation.RETURN,
                new ReturnOperation(storage));
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void getHandler_ShouldReturnCorrectHandler_WhenOperationExists() {
        assertTrue(operationStrategy.getHandler(FruitTransaction.Operation.BALANCE)
                instanceof BalanceOperation);
        assertTrue(operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY)
                instanceof SupplyOperation);
        assertTrue(operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE)
                instanceof PurchaseOperation);
        assertTrue(operationStrategy.getHandler(FruitTransaction.Operation.RETURN)
                instanceof ReturnOperation);
    }

    @Test
    void getHandler_ShouldReturnNull_WhenOperationNotInMap() {
        Map<FruitTransaction.Operation, OperationHandler>
                incompleteHandlers = new EnumMap<>(FruitTransaction.Operation.class);
        incompleteHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation(storage));
        OperationStrategyImpl incompleteStrategy = new OperationStrategyImpl(incompleteHandlers);
        assertNull(incompleteStrategy.getHandler(FruitTransaction.Operation.SUPPLY));
    }

    @Test
    void getHandler_ShouldReturnNonNullHandlers_ForAllSupportedOperations() {
        assertNotNull(operationStrategy.getHandler(FruitTransaction.Operation.BALANCE));
        assertNotNull(operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY));
        assertNotNull(operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE));
        assertNotNull(operationStrategy.getHandler(FruitTransaction.Operation.RETURN));
    }

    @Test
    void getHandler_ShouldHandleBalanceOperationCorrectly() {
        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        balanceTransaction.setFruit("apple");
        balanceTransaction.setQuantity(50);
        operationStrategy.getHandler(FruitTransaction.Operation.BALANCE).handle(balanceTransaction);
        assertEquals(50, storage.getFruitQuantity("apple"));
    }

    @Test
    void getHandler_ShouldHandleSupplyOperationCorrectly() {
        FruitTransaction supplyTransaction = new FruitTransaction();
        supplyTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        supplyTransaction.setFruit("banana");
        supplyTransaction.setQuantity(30);
        operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY).handle(supplyTransaction);
        assertEquals(30, storage.getFruitQuantity("banana"));
    }

    @Test
    void getHandler_ShouldHandlePurchaseOperationCorrectly() {
        storage.updateFruitQuantity("orange", 40);
        FruitTransaction purchaseTransaction = new FruitTransaction();
        purchaseTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseTransaction.setFruit("orange");
        purchaseTransaction.setQuantity(15);
        operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE)
                .handle(purchaseTransaction);
        assertEquals(25, storage.getFruitQuantity("orange"));
    }

    @Test
    void getHandler_ShouldHandleReturnOperationCorrectly() {
        FruitTransaction returnTransaction = new FruitTransaction();
        returnTransaction.setOperation(FruitTransaction.Operation.RETURN);
        returnTransaction.setFruit("grape");
        returnTransaction.setQuantity(10);
        operationStrategy.getHandler(FruitTransaction.Operation.RETURN).handle(returnTransaction);
        assertEquals(10, storage.getFruitQuantity("grape"));
    }

}
