package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private static final String FRUIT_NAME = "apple";
    private static final int QUANTITY = 50;
    private OperationStrategyImpl operationStrategy;
    private Map<String, OperationHandler> operations;

    @BeforeEach
    public void setUp() {
        operations = new HashMap<>();
        operations.put("b", new BalanceOperation());
        operations.put("p", new PurchaseOperation());
        operations.put("r", new ReturnOperation());
        operations.put("s", new SupplyOperation());

        operationStrategy = new OperationStrategyImpl(operations);
    }

    @Test
    public void getHandler_inputBalanceOperation_Ok() {
        Transaction transaction = new Transaction("b", FRUIT_NAME, QUANTITY);
        var expected = operations.get("b");
        var actual = operationStrategy.getHandler(transaction);
        assertEquals(expected, actual);
    }
}
