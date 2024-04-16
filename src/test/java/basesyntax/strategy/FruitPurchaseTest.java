package basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import basesyntax.db.Storage;
import basesyntax.exceptions.TransactionException;
import basesyntax.model.Fruit;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitPurchaseTest {
    private static final Map<String, Fruit> fruitsData = Storage.getFruits();
    private static final String FRUIT_NAME = "banana";
    private static final String NO_EXISTING_FRUIT_NAME = "apple";
    private static final int FRUIT_BALANCE_QUANTITY = 40;
    private static final int FRUIT_PURCHASE_QUANTITY = 20;
    private static final FruitPurchase fruitPurchase = new FruitPurchase();

    @BeforeEach
    void setUp() {
        fruitsData.put(FRUIT_NAME, new Fruit(FRUIT_NAME, FRUIT_BALANCE_QUANTITY));
    }

    @Test
    void transactionHandler_fruitsDataNull_NotOk() {
        assertThrows(TransactionException.class, () -> {
            fruitPurchase.transactionHandler(null, FRUIT_NAME, FRUIT_PURCHASE_QUANTITY);
        });
    }

    @Test
    void transactionHandler_fruitNameNull_NotOk() {
        assertThrows(TransactionException.class, () -> {
            fruitPurchase.transactionHandler(fruitsData, null, FRUIT_PURCHASE_QUANTITY);
        });
    }

    @Test
    void transactionHandler_noFruit_NotOk() {
        assertThrows(TransactionException.class, () -> {
            fruitPurchase.transactionHandler(
                    fruitsData, NO_EXISTING_FRUIT_NAME, FRUIT_PURCHASE_QUANTITY
            );
        });
    }

    @Test
    void transactionHandler_notEnoughFruits_NotOk() {
        assertThrows(TransactionException.class, () -> {
            fruitPurchase.transactionHandler(
                    fruitsData,
                    FRUIT_NAME,
                    FRUIT_BALANCE_QUANTITY + FRUIT_PURCHASE_QUANTITY
            );
        });
    }

    @Test
    void transactionHandler_purchase_Ok() {
        fruitPurchase.transactionHandler(fruitsData, FRUIT_NAME, FRUIT_PURCHASE_QUANTITY);
        int expectedQuantity = FRUIT_BALANCE_QUANTITY - FRUIT_PURCHASE_QUANTITY;
        int actualQuantity = fruitsData.get(FRUIT_NAME).getQuantity();
        assertEquals(actualQuantity, expectedQuantity);
    }

    @AfterAll
    static void afterAll() {
        fruitsData.clear();
    }
}
