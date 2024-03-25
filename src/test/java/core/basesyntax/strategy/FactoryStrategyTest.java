package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import core.basesyntax.model.FruitOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FactoryStrategyTest {
    private FactoryStrategy factoryStrategy;
    private Map<FruitOperation, OperationHandler> mockStrategyType;

    @BeforeEach
    public void setUp() {
        mockStrategyType = new HashMap<>();
        factoryStrategy = new FactoryStrategy(mockStrategyType);
    }

    @Test
    public void getFruitService_ExistingOperation_Ok() {
        OperationHandler expectedHandler = mock(OperationHandler.class);
        FruitOperation operation = FruitOperation.BALANCE;
        mockStrategyType.put(operation, expectedHandler);
        OperationHandler actualHandler = factoryStrategy.getFruitService(operation);
        assertEquals(expectedHandler, actualHandler);
    }

    @Test
    public void getFruitService_NonExistingOperation_notOk() {
        FruitOperation operation = FruitOperation.BALANCE;
        OperationHandler actualHandler = factoryStrategy.getFruitService(operation);
        assertEquals(null, actualHandler);
    }
}
