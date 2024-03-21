package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.impl.BalanceOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private static final Integer QUANTITY = 12;
    private static final String FRUIT = "banana";
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new BalanceOperation();
        Storage.fruits.clear();
    }

    @Test
    void handleFruitOperation_balance_ok() {
        operationHandler.handleFruitOperation(FRUIT, QUANTITY);
        assertEquals(QUANTITY, Storage.fruits.get(FRUIT));
    }

    @Test
    void handleFruitOperation_whenFruitExists_ok() {
        Storage.fruits.put(FRUIT, QUANTITY);
        operationHandler.handleFruitOperation(FRUIT, QUANTITY);
        assertEquals(Integer.valueOf(QUANTITY + QUANTITY), Storage.fruits.get(FRUIT));
    }
}
