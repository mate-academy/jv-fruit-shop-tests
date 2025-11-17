package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static final String FRUIT = "apple";

    @BeforeEach
    void resetStorage() {
        Storage.put(FRUIT, null);
    }

    @Test
    void positivePurchaseHandler() {
        Storage.put(FRUIT, 30);

        PurchaseHandler handler = new PurchaseHandler();
        handler.apply(new FruitTransaction(Operation.PURCHASE, FRUIT, 5));

        assertEquals(Integer.valueOf(25), Storage.getOrDefault(FRUIT, 0));
    }

    @Test
    void notEnoughItems() {
        Storage.put(FRUIT, 3);

        PurchaseHandler handler = new PurchaseHandler();

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> handler.apply(new FruitTransaction(Operation.PURCHASE, FRUIT, 5))
        );

        assertEquals("There is not enough items to make a purchase", ex.getMessage());
        assertEquals(Integer.valueOf(3), Storage.getOrDefault(FRUIT, 0));
    }
}
