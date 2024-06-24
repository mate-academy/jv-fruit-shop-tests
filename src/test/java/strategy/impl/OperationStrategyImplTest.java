package strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import model.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import strategy.OperationHandler;
import strategy.OperationStrategy;

class OperationStrategyImplTest {
    private static final Map<Operation, OperationHandler> startegyMap = Map.of(
            Operation.BALANCE, new BalanceOperation(),
            Operation.PURCHASE, new PurchaseOperation(),
            Operation.RETURN, new ReturnOperation(),
            Operation.SUPPLY, new SupplyOperation()
    );
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        operationStrategy = new OperationStrategyImpl(startegyMap);
    }

    @Test
    void getOperationHandler_fromNullOperation_notOk() {
        assertThrows(RuntimeException.class, () -> operationStrategy.getOperationHandler(null));
    }

    @Test
    void getOperationHandler_Ok() {
        for (Map.Entry<Operation, OperationHandler> pair : startegyMap.entrySet()) {
            assertEquals(pair.getValue(), operationStrategy.getOperationHandler(pair.getKey()));
        }
    }
}
