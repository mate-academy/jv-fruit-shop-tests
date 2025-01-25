package core.basesyntax.model.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.strategy.OperationStrategy;
import core.basesyntax.model.strategy.OperationStrategyImpl;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationHandlerTest {
    private static final Map<FruitTransaction.Operation, OperationHandler> startegyMap = Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceOperation(),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
            FruitTransaction.Operation.RETURN, new ReturnOperation(),
            FruitTransaction.Operation.SUPPLY, new SupplyOperation()
    );
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        operationStrategy = new OperationStrategyImpl(startegyMap);
    }

    @Test
    void getOperationHandler_Ok() {
        for (Map.Entry<FruitTransaction.Operation, OperationHandler> pair
                : startegyMap.entrySet()) {
            assertEquals(pair.getValue(), operationStrategy.getOperationHandler(pair.getKey()));
        }
    }
}
