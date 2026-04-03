package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static final int INITIAL_QUANTITY = 100;
    private static final String BANANA = "banana";

    private Storage storage;
    private OperationHandler handler;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        handler = new BalanceOperation(storage);
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void handle_setQuantity_ok() {
        fruitTransaction.setFruit(BANANA);
        fruitTransaction.setQuantity(INITIAL_QUANTITY);
        handler.handle(fruitTransaction);
        assertEquals(INITIAL_QUANTITY, storage.getFruits().get(BANANA));
    }
}
