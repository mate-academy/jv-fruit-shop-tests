package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.operation.BalanceHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseHandler;
import core.basesyntax.operation.ReturnHandler;
import core.basesyntax.operation.SupplyHandler;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.transaction.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private FruitStorage fruitStorage;
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> handlerMap;

    @BeforeEach
    void setUp() {
        handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler(fruitStorage));
        handlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler(fruitStorage));
        handlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(fruitStorage));
        handlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler(fruitStorage));
        operationStrategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    void get_forReturn_success() {
        OperationHandler returnOperation = operationStrategy.get(FruitTransaction.Operation.RETURN);
        assertNotNull(returnOperation);
        assertEquals(ReturnHandler.class, returnOperation.getClass());
    }

    @Test
    void get_forPurchase_success() {
        OperationHandler purchaseHandler = operationStrategy
                .get(FruitTransaction.Operation.PURCHASE);
        assertNotNull(purchaseHandler);
        assertEquals(PurchaseHandler.class, purchaseHandler.getClass());
    }

    @Test
    void get_forSupply_success() {
        OperationHandler supplyHandler = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertNotNull(supplyHandler);
        assertEquals(SupplyHandler.class, supplyHandler.getClass());
    }

    @Test
    void get_forBalance_success() {
        OperationHandler balanceHandler = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertNotNull(balanceHandler);
        assertEquals(BalanceHandler.class, balanceHandler.getClass());
    }

    @Test
    void get_forNull_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                operationStrategy.get(null),
                "Expected IllegalArgumentException if no handler is found for the operation");
    }
}
