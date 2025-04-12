package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.operationhandler.BalanceOperation;
import core.basesyntax.service.operationhandler.OperationHandler;
import core.basesyntax.service.operationhandler.PurchaseOperation;
import core.basesyntax.service.operationhandler.ReturnOperation;
import core.basesyntax.service.operationhandler.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy strategy;
    private Map<FruitTransaction.Operation, OperationHandler> allHandlers;

    @BeforeEach
    void setUp() {
        allHandlers = new HashMap<>();
        allHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        allHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        allHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        allHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        strategy = new OperationStrategyImpl(allHandlers);
    }

    @Test
    void testStrategyWithAllOperationHandlers_Ok() {
        assertInstanceOf(BalanceOperation.class,
                strategy.getHandler(FruitTransaction.Operation.BALANCE));
        assertInstanceOf(SupplyOperation.class,
                strategy.getHandler(FruitTransaction.Operation.SUPPLY));
        assertInstanceOf(PurchaseOperation.class,
                strategy.getHandler(FruitTransaction.Operation.PURCHASE));
        assertInstanceOf(ReturnOperation.class,
                strategy.getHandler(FruitTransaction.Operation.RETURN));
    }

    @Test
    void testGetHandlerForNullOperation_NotOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            strategy.getHandler(null);
        });
    }

}
