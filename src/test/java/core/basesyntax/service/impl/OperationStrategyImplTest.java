package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private OperationHandler balanceHandler;
    private OperationHandler supplyHandler;
    private OperationHandler purchaseHandler;
    private OperationHandler returnHandler;

    @BeforeEach
    void setUp() {
        balanceHandler = new BalanceOperation();
        supplyHandler = new SupplyOperation();
        purchaseHandler = new PurchaseOperation();
        returnHandler = new ReturnOperation();

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, supplyHandler);
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);
        operationHandlers.put(FruitTransaction.Operation.RETURN, returnHandler);

        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void test_get_balanceOperation_returnsBalanceHandler() {
        assertEquals(balanceHandler, operationStrategy.get(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void test_get_supplyOperation_returnsSupplyHandler() {
        assertEquals(supplyHandler, operationStrategy.get(FruitTransaction.Operation.SUPPLY));
    }

    @Test
    void test_get_purchaseOperation_returnsPurchaseHandler() {
        assertEquals(purchaseHandler, operationStrategy.get(FruitTransaction.Operation.PURCHASE));
    }

    @Test
    void test_get_returnOperation_returnsReturnHandler() {
        assertEquals(returnHandler, operationStrategy.get(FruitTransaction.Operation.RETURN));
    }

    @Test
    void test_get_unknownOperation_returnsNull() {
        assertNull(operationStrategy.get(null));
    }

    @Test
    void test_get_nonExistingOperation_returnsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                operationStrategy.get(FruitTransaction.Operation.fromCode("d")));
    }

    @Test
    void test_get_emptyMap_throwsException() {
        assertThrows(RuntimeException.class, () -> new OperationStrategyImpl(new HashMap<>()));
    }
}
