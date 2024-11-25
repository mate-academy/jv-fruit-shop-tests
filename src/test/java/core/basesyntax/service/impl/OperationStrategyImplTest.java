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
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String ORANGE = "orange";
    private static final String GRAPE = "grape";
    private static final int BALANCE_QUANTITY = 50;
    private static final int SUPPLY_QUANTITY = 30;
    private static final int INITIAL_ORANGE_QUANTITY = 40;
    private static final int PURCHASE_QUANTITY = 15;
    private static final int EXPECTED_ORANGE_QUANTITY_AFTER_PURCHASE = 25;
    private static final int RETURN_QUANTITY = 10;
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
        balanceTransaction.setFruit(APPLE);
        balanceTransaction.setQuantity(BALANCE_QUANTITY);
        operationStrategy.getHandler(FruitTransaction.Operation.BALANCE).handle(balanceTransaction);
        assertEquals(BALANCE_QUANTITY, storage.getFruitQuantity(APPLE));
    }

    @Test
    void getHandler_ShouldHandleSupplyOperationCorrectly() {
        FruitTransaction supplyTransaction = new FruitTransaction();
        supplyTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        supplyTransaction.setFruit(BANANA);
        supplyTransaction.setQuantity(SUPPLY_QUANTITY);
        operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY).handle(supplyTransaction);
        assertEquals(SUPPLY_QUANTITY, storage.getFruitQuantity(BANANA));
    }

    @Test
    void getHandler_ShouldHandlePurchaseOperationCorrectly() {
        storage.updateFruitQuantity(ORANGE, INITIAL_ORANGE_QUANTITY);
        FruitTransaction purchaseTransaction = new FruitTransaction();
        purchaseTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseTransaction.setFruit(ORANGE);
        purchaseTransaction.setQuantity(PURCHASE_QUANTITY);
        operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE)
                .handle(purchaseTransaction);
        assertEquals(EXPECTED_ORANGE_QUANTITY_AFTER_PURCHASE, storage.getFruitQuantity(ORANGE));
    }

    @Test
    void getHandler_ShouldHandleReturnOperationCorrectly() {
        FruitTransaction returnTransaction = new FruitTransaction();
        returnTransaction.setOperation(FruitTransaction.Operation.RETURN);
        returnTransaction.setFruit(GRAPE);
        returnTransaction.setQuantity(RETURN_QUANTITY);
        operationStrategy.getHandler(FruitTransaction.Operation.RETURN).handle(returnTransaction);
        assertEquals(RETURN_QUANTITY, storage.getFruitQuantity(GRAPE));
    }

}
