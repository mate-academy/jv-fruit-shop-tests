package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationHandlerStrategyImplTest {
    private OperationHandlerStrategy operationHandlerStrategy;
    private final Map<FruitTransaction.Operation, OperationHandler> strategyMapForTest =
            Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                    FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(),
                    FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                    FruitTransaction.Operation.RETURN, new ReturnOperationHandler());

    @BeforeEach
    void setUp() {
        operationHandlerStrategy = new OperationHandlerStrategyImpl(strategyMapForTest);
    }

    @Test
    void getOperationHandler_returnCorrectOperationHandlerObject_ok() {
        FruitTransaction.Operation supply = FruitTransaction.Operation.SUPPLY;
        Class<? extends OperationHandler> expected = SupplyOperationHandler.class;
        Class<? extends OperationHandler> actual =
                operationHandlerStrategy.getOperationHandler(supply).getClass();
        assertEquals(expected, actual);
    }

    @Test
    void getOperationHandler_checkCorrectSizeInputMapHandler_ok() {
        int expected = FruitTransaction.Operation.values().length;
        int actual = strategyMapForTest.size();
        assertEquals(expected, actual);
    }
}
