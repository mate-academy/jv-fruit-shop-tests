package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.impl.SupplyOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private static final Integer QUANTITY = 12;
    private static final String FRUIT = "banana";
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new SupplyOperation();
        Storage.fruits.clear();
    }

    @Test
    void supply_withNewFruit_ok() {
        operationHandler.handleFruitOperation(FRUIT, QUANTITY);
        assertEquals(QUANTITY, Storage.fruits.get(FRUIT));
    }

    @Test
    void supply_withExistingFruit_ok() {
        Storage.fruits.put(FRUIT, QUANTITY);
        operationHandler.handleFruitOperation(FRUIT, QUANTITY);
        assertEquals(Integer.valueOf(QUANTITY + QUANTITY), Storage.fruits.get(FRUIT));
    }
}
