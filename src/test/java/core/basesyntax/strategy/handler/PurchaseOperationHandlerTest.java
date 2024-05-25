package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final String BANANA = "banana";
    private static final int DEFAULT_QUANTITY = 10;
    private static final int NEGATIVE_QUANTITY = -1;
    private static final int ZERO_QUANTITY = 0;
    private static OperationHandler handler;

    @BeforeAll
    static void beforeAll() {
        handler = new PurchaseOperationHandler();
    }

    @AfterEach
    void afterEach() {
        Storage.fruits.clear();
    }

    @Test
    public void purchaseHandle_normalQuantity_ok() {
        Storage.fruits.put(BANANA, DEFAULT_QUANTITY);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, BANANA, DEFAULT_QUANTITY);
        handler.process(fruitTransaction);
        assertEquals(0, Storage.fruits.get(BANANA));
    }

    @Test
    public void purchaseHandle_illegalQuantity_ok() {
        Storage.fruits.put(BANANA, DEFAULT_QUANTITY);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, BANANA, ZERO_QUANTITY);

        assertThrows(IllegalArgumentException.class, () -> handler.process(fruitTransaction));
        assertEquals(DEFAULT_QUANTITY, Storage.fruits.get(BANANA));
    }

    @Test
    void purchaseHandle_negativeQuantity_notOk() {
        Storage.fruits.put(BANANA, DEFAULT_QUANTITY);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, BANANA, NEGATIVE_QUANTITY);

        assertThrows(IllegalArgumentException.class, () -> handler.process(fruitTransaction));
        assertEquals(DEFAULT_QUANTITY, Storage.fruits.get(BANANA));
    }
}
