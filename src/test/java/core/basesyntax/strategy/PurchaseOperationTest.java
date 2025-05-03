package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.NotEnoughFruitException;
import core.basesyntax.strategy.impl.PurchaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private static final int QUANTITY = 12;
    private static final int INVALID_QUANTITY = 120;
    private static final String FRUIT = "banana";
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new PurchaseOperation();
        Storage.fruits.clear();
    }

    @Test
    void purchase_withSufficientQuantity_ok() {
        Storage.fruits.put(FRUIT, QUANTITY);
        operationHandler.handleFruitOperation(FRUIT, QUANTITY);
        Integer expected = QUANTITY - QUANTITY;
        assertEquals(expected, Storage.fruits.get(FRUIT));
    }

    @Test
    void purchase_withInsufficientQuantity_notOk() {
        Storage.fruits.put(FRUIT, QUANTITY);
        assertThrows(NotEnoughFruitException.class, () -> {
            operationHandler.handleFruitOperation(FRUIT, INVALID_QUANTITY);
        });
    }
}
