package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static final int INITIAL_QUANTITY = 20;
    private static final int PURCHASE_AMOUNT = 5;
    private static final int EXPECTED_QUANTITY = 15;
    private static final int TOO_BIG_PURCHASE = 25;
    private static final String BANANA = "banana";

    private Storage storage;
    private OperationHandler handler;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        handler = new PurchaseOperation(storage);
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void handle_minusQuantity_ok() {
        storage.getFruits().put(BANANA, INITIAL_QUANTITY);
        fruitTransaction.setFruit(BANANA);
        fruitTransaction.setQuantity(PURCHASE_AMOUNT);
        handler.handle(fruitTransaction);
        assertEquals(EXPECTED_QUANTITY, storage.getFruits().get(BANANA));
    }

    @Test
    void handle_minusQuantity_notOk() {
        storage.getFruits().put(BANANA, INITIAL_QUANTITY);
        fruitTransaction.setFruit(BANANA);
        fruitTransaction.setQuantity(TOO_BIG_PURCHASE);
        assertThrows(RuntimeException.class, () -> {
            handler.handle(fruitTransaction);
        });
        assertEquals(INITIAL_QUANTITY, storage.getFruits().get(BANANA));
    }
}
