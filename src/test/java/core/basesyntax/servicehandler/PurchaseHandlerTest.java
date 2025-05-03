package core.basesyntax.servicehandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerTest {

    private static final String FRUIT_APPLE = "apple";
    private static final int INITIAL_QUANTITY = 10;
    private static final int PURCHASE_QUANTITY = 5;
    private static final int INSUFFICIENT_QUANTITY = 3;
    private static final String ERROR_INSUFFICIENT_STOCK = "Insufficient quantity of fruit: "
            + FRUIT_APPLE + ". Available quantity: " + INSUFFICIENT_QUANTITY
            + ", requested quantity: " + PURCHASE_QUANTITY;
    private static final String ERROR_NON_EXISTENT_FRUIT = "Insufficient quantity of fruit: "
            + FRUIT_APPLE + ". Available quantity: 0, requested quantity: " + PURCHASE_QUANTITY;

    private Map<String, Integer> fruitStorage;

    @BeforeEach
    public void setUp() {
        fruitStorage = new HashMap<>();
    }

    @Test
    public void testPurchaseHandler_Success_ok() {
        fruitStorage.put(FRUIT_APPLE, INITIAL_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                FRUIT_APPLE, PURCHASE_QUANTITY);
        PurchaseHandler handler = new PurchaseHandler(fruitStorage);
        handler.handle(transaction);

        assertEquals(INITIAL_QUANTITY - PURCHASE_QUANTITY, fruitStorage.get(FRUIT_APPLE));
    }

    @Test
    public void testPurchaseHandler_InsufficientStock_notOk() {
        fruitStorage.put(FRUIT_APPLE, INSUFFICIENT_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                FRUIT_APPLE, PURCHASE_QUANTITY);
        PurchaseHandler handler = new PurchaseHandler(fruitStorage);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            handler.handle(transaction);
        });
        assertEquals(ERROR_INSUFFICIENT_STOCK, exception.getMessage());
    }

    @Test
    public void testPurchaseHandler_NonExistentFruit_notOk() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                FRUIT_APPLE, PURCHASE_QUANTITY);
        PurchaseHandler handler = new PurchaseHandler(fruitStorage);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            handler.handle(transaction);
        });
        assertEquals(ERROR_NON_EXISTENT_FRUIT, exception.getMessage());
    }
}
