package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private FruitTransaction fruitTransaction;
    private ReturnOperationHandler returnOperationHandler = new ReturnOperationHandler();

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(BANANA);
        fruitTransaction.setQuantity(10);
        Storage.SHOPSTORAGE.clear();
    }

    @Test
    void handle_return_ok() {
        returnOperationHandler.handle(fruitTransaction);
        assertEquals(10, Storage.SHOPSTORAGE.get(BANANA));
    }

    @Test
    void handle_returnAddStorage_ok() {
        Storage.SHOPSTORAGE.put(BANANA, 10);
        returnOperationHandler.handle(fruitTransaction);
        assertEquals(20, Storage.SHOPSTORAGE.get(BANANA));
    }

    @Test
    void handle_returnApple_ok() {
        FruitTransaction fruitApple = new FruitTransaction();
        fruitApple.setFruit(APPLE);
        fruitApple.setQuantity(20);
        returnOperationHandler.handle(fruitApple);
        assertEquals(20, Storage.SHOPSTORAGE.get(APPLE));
    }
}
