package core.basesyntax.service.strategy;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.db.Storage;
import core.basesyntax.service.exceptions.ProductQuantityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private Storage storage;
    private BalanceHandler handler;
    private PurchaseHandler purchaseHandler;
    private final String fruit = "apple";
    private final int quantity = 20;
    private final int subtractQuantity = 5;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        handler = new BalanceHandler(storage);
        purchaseHandler = new PurchaseHandler(storage);
    }

    @Test
    void put_newInformationToStorage_Ok() {
        assertNull(storage.getData().get(fruit),
                "Fruit should not exist in storage before operation.");
        handler.operate(fruit, quantity);

        int actualQuantity = quantity - subtractQuantity;
        purchaseHandler.operate(fruit, subtractQuantity);

        assertEquals(actualQuantity, storage.getData().get(fruit),
                "Storage does not contain the correct quantity of the fruit after operation.");
    }

    @Test
    void throw_productQuantityException_Ok() {
        handler.operate(fruit, quantity);

        ProductQuantityException exception = assertThrows(ProductQuantityException.class,
                () -> purchaseHandler.operate(fruit, 20));
        assertEquals("Attempting to remove more "
                        + fruit + " than available.", exception.getMessage(),
                "Incorrect validation error message");
    }
}
