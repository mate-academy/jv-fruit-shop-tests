package core.basesyntax.service.strategy;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.exceptions.ProductQuantityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static final String FRUIT = "apple";
    private static final int QUANTITY = 20;
    private static final int SUBTRACT_QUANTITY = 5;
    private final Storage storage = new Storage();
    private PurchaseHandler purchaseHandler;

    @BeforeEach
    void setUp() {
        purchaseHandler = new PurchaseHandler(storage);
        storage.getData().clear();
    }

    @Test
    void put_changedInformationToStorage_Ok() {
        storage.getData().put(FRUIT, QUANTITY);
        int expectedQuantity = QUANTITY - SUBTRACT_QUANTITY;
        purchaseHandler.operate(FRUIT, SUBTRACT_QUANTITY);

        assertEquals(expectedQuantity, storage.getData().get(FRUIT),
                "Storage does not contain the correct quantity of the fruit after operation.");
    }

    @Test
    void put_productQuantityException_notOk() {
        storage.getData().put(FRUIT, QUANTITY);

        ProductQuantityException exception = assertThrows(ProductQuantityException.class,
                () -> purchaseHandler.operate(FRUIT, QUANTITY));
        assertEquals("Attempting to remove more "
                        + FRUIT + " than available.", exception.getMessage(),
                "Incorrect validation error message");
    }
}
