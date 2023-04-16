package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    @Test
    void get_Ok() {
        OperationHandler balanceHandler = new BalanceTransactionHandlerImpl();
        OperationHandler purchaseHandler = new PurchaseTransactionHandlerImpl();
        OperationHandler returnHandler = new ReturnTransactionHandlerImpl();
        OperationHandler supplyHandler = new SupplyTransactionHandlerImpl();

        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        handlerMap.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);
        handlerMap.put(FruitTransaction.Operation.RETURN, returnHandler);
        handlerMap.put(FruitTransaction.Operation.SUPPLY, supplyHandler);
        OperationStrategyImpl strategy = new OperationStrategyImpl(handlerMap);

        assertEquals(balanceHandler, strategy.get(FruitTransaction.Operation.BALANCE));
        assertEquals(purchaseHandler, strategy.get(FruitTransaction.Operation.PURCHASE));
        assertEquals(returnHandler, strategy.get(FruitTransaction.Operation.RETURN));
        assertEquals(supplyHandler, strategy.get(FruitTransaction.Operation.SUPPLY));
    }

    @Test
    void testGetUnknownOperation() {
        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        OperationStrategyImpl strategy = new OperationStrategyImpl(handlerMap);
        assertNull(strategy.get(FruitTransaction.Operation.BALANCE));
    }
}
