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

class FruitReturnTest {
    private static final Map<String, Fruit> fruitsData = Storage.getFruits();
    private static final String FRUIT_NAME = "banana";
    private static final String NO_EXISTING_FRUIT_NAME = "apple";
    private static final int FRUIT_BALANCE_QUANTITY = 40;
    private static final int FRUIT_RETURN_QUANTITY = 20;
    private static final FruitReturn fruitReturn = new FruitReturn();

    @BeforeEach
    void setUp() {
        fruitsData.put(FRUIT_NAME, new Fruit(FRUIT_NAME, FRUIT_BALANCE_QUANTITY));
    }

    @Test
    void transactionHandler_fruitsDataNull_NotOk() {
        assertThrows(TransactionException.class, () -> {
            fruitReturn.transactionHandler(null, FRUIT_NAME, FRUIT_RETURN_QUANTITY);
        });
    }

    @Test
    void transactionHandler_fruitNameNull_NotOk() {
        assertThrows(TransactionException.class, () -> {
            fruitReturn.transactionHandler(fruitsData, null, FRUIT_RETURN_QUANTITY);
        });
    }

    @Test
    void transactionHandler_noFruit_Ok() {
        fruitReturn.transactionHandler(fruitsData, NO_EXISTING_FRUIT_NAME, FRUIT_RETURN_QUANTITY);
        int fruitQuantityActual = fruitsData.get(NO_EXISTING_FRUIT_NAME).getQuantity();
        int actualFruitsKindsQuantity = fruitsData.size();
        int expectedFruitsKindsQuantity = 2;
        assertEquals(fruitQuantityActual, FRUIT_RETURN_QUANTITY);
        assertEquals(actualFruitsKindsQuantity, expectedFruitsKindsQuantity);
    }

    @Test
    void transactionHandler_supply_Ok() {
        fruitReturn.transactionHandler(fruitsData, FRUIT_NAME, FRUIT_RETURN_QUANTITY);
        int fruitQuantityActual = fruitsData.get(FRUIT_NAME).getQuantity();
        int expectedFruitsKindsQuantity = FRUIT_BALANCE_QUANTITY + FRUIT_RETURN_QUANTITY;
        assertEquals(fruitQuantityActual, expectedFruitsKindsQuantity);
    }

    @AfterAll
    static void afterAll() {
        fruitsData.clear();
    }
}
