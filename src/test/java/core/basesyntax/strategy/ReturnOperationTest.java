package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.impl.ReturnOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private static final Integer QUANTITY = 12;
    private static final String FRUIT = "banana";
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new ReturnOperation();
        Storage.fruits.clear();
    }

    @Test
    void return_withNewFruit_ok() {
        operationHandler.handleFruitOperation(FRUIT, QUANTITY);
        assertEquals(QUANTITY, Storage.fruits.get(FRUIT));
    }

    @Test
    void return_withExistingFruit_ok() {
        Storage.fruits.put(FRUIT, QUANTITY);
        operationHandler.handleFruitOperation(FRUIT, QUANTITY);
        assertEquals(Integer.valueOf(QUANTITY + QUANTITY), Storage.fruits.get(FRUIT));
    }
}
