package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void get_ValidOperation_ok() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        Assertions.assertEquals(PurchaseOperation.class, handler.getClass(),
                "Strategy should return BalanceOperation for Purchase type");
    }

    @Test
    void get_nullOperation_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            operationStrategy.get(null);
        },"Strategy should throw exception for unknown or null operation");
    }
}
