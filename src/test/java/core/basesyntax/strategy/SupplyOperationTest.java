package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static final int INITIAL_QUANTITY = 20;
    private static final int ADDED_QUANTITY = 30;
    private static final int EXPECTED_QUANTITY = 50;
    private static final String BANANA = "banana";

    private Storage storage;
    private OperationHandler handler;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        handler = new SupplyOperation(storage);
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void handle_plusQuantity_ok() {
        storage.getFruits().put(BANANA, INITIAL_QUANTITY);
        fruitTransaction.setFruit(BANANA);
        fruitTransaction.setQuantity(ADDED_QUANTITY);
        handler.handle(fruitTransaction);
        assertEquals(EXPECTED_QUANTITY, storage.getFruits().get(BANANA));
    }
}
