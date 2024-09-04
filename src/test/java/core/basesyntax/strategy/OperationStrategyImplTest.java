package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    @Test
    void choseOperationHandler_ValidOperation_Ok() {
        OperationHandler balanceHandler = new BalanceOperation();
        OperationHandler purchaseHandler = new PurchaseOperation();
        OperationHandler returnHandler = new ReturnOperation();
        OperationHandler supplyHandler = new SupplyOperation();

        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        handlerMap.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);
        handlerMap.put(FruitTransaction.Operation.RETURN, returnHandler);
        handlerMap.put(FruitTransaction.Operation.SUPPLY, supplyHandler);
        OperationStrategyImpl strategy = new OperationStrategyImpl(handlerMap);

        assertEquals(balanceHandler,
                strategy.choseOperationHandler(FruitTransaction.Operation.BALANCE));
        assertEquals(purchaseHandler,
                strategy.choseOperationHandler(FruitTransaction.Operation.PURCHASE));
        assertEquals(returnHandler,
                strategy.choseOperationHandler(FruitTransaction.Operation.RETURN));
        assertEquals(supplyHandler,
                strategy.choseOperationHandler(FruitTransaction.Operation.SUPPLY));
    }

    @Test
    void choseOperationHandler_UnknownOperation_NotOk() {
        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        OperationStrategyImpl strategy = new OperationStrategyImpl(handlerMap);
        assertNull(strategy.choseOperationHandler(FruitTransaction.Operation.BALANCE));
    }
}
