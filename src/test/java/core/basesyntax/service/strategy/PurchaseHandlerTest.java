package core.basesyntax.service.strategy;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.db.Storage;
import core.basesyntax.service.exceptions.ProductQuantityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static final String FRUIT = "apple";
    private static final int QUANTITY = 20;
    private static final int SUBTRACT_QUANTITY = 5;
    private final Storage storage = new Storage();
    private BalanceHandler handler;
    private PurchaseHandler purchaseHandler;

    @BeforeEach
    void setUp() {
        handler = new BalanceHandler(storage);
        purchaseHandler = new PurchaseHandler(storage);
    }

    @Test
    void put_changedInformationToStorage_Ok() {
        assertNull(storage.getData().get(FRUIT),
                "Fruit should not exist in storage before operation.");
        handler.operate(FRUIT, QUANTITY);

        int actualQuantity = QUANTITY - SUBTRACT_QUANTITY;
        purchaseHandler.operate(FRUIT, SUBTRACT_QUANTITY);

        assertEquals(actualQuantity, storage.getData().get(FRUIT),
                "Storage does not contain the correct quantity of the fruit after operation.");
    }

    @Test
    void put_productQuantityException_notOk() {
        handler.operate(FRUIT, QUANTITY);

        ProductQuantityException exception = assertThrows(ProductQuantityException.class,
                () -> purchaseHandler.operate(FRUIT, QUANTITY));
        assertEquals("Attempting to remove more "
                        + FRUIT + " than available.", exception.getMessage(),
                "Incorrect validation error message");
    }
}
