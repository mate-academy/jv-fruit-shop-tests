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

class FruitBalanceTest {
    private static final Map<String, Fruit> fruitsData = Storage.getFruits();
    private static final String FRUIT_NAME = "banana";
    private static final String NO_EXISTING_FRUIT_NAME = "apple";
    private static final int FRUIT_BALANCE_QUANTITY = 40;
    private static final int FRUIT_BALANCE_DIFFERENT_QUANTITY = 40;
    private static final FruitBalance fruitBalance = new FruitBalance();

    @BeforeEach
    void setUp() {
        fruitsData.put(FRUIT_NAME, new Fruit(FRUIT_NAME, FRUIT_BALANCE_QUANTITY));
    }

    @Test
    void transactionHandler_fruitsDataNull_NotOk() {
        assertThrows(TransactionException.class, () -> {
            fruitBalance.transactionHandler(null, FRUIT_NAME, FRUIT_BALANCE_QUANTITY);
        });
    }

    @Test
    void transactionHandler_fruitNameNull_NotOk() {
        assertThrows(TransactionException.class, () -> {
            fruitBalance.transactionHandler(fruitsData, null, FRUIT_BALANCE_QUANTITY);
        });
    }

    @Test
    void transactionHandler_differentQuantity_Ok() {
        fruitBalance.transactionHandler(fruitsData, FRUIT_NAME, FRUIT_BALANCE_DIFFERENT_QUANTITY);
        assertEquals(fruitsData.get(FRUIT_NAME).getQuantity(), FRUIT_BALANCE_DIFFERENT_QUANTITY);
    }

    @Test
    void transactionHandler_differentFruit_Ok() {
        fruitBalance.transactionHandler(
                fruitsData, NO_EXISTING_FRUIT_NAME, FRUIT_BALANCE_DIFFERENT_QUANTITY
        );
        int expectedFruitsDataSize = 2;
        int actualFruitDataSize = fruitsData.size();
        assertEquals(actualFruitDataSize, expectedFruitsDataSize);
    }

    @AfterAll
    static void afterAll() {
        fruitsData.clear();
    }
}
