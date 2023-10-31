package core.basesyntax.serviceimpl;

import core.basesyntax.service.OperationService;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitStrategyTest {
    private FruitStrategy fruitStrategy;

    @BeforeEach
    void setUp() {
        fruitStrategy = new FruitStrategy(Map.of(FruitTransaction.Operation.RETURN,
                new BalanceOperationHandler()));
    }

    @Test
    void getOperationService_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "apple",10);
        OperationService handler = fruitStrategy.getOperationService(fruitTransaction
                .getOperation());
        Assertions.assertNotNull(handler);
        Assertions.assertTrue(handler instanceof BalanceOperationHandler);
    }
}
