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

class FruitSupplyTest {
    private static final Map<String, Fruit> fruitsData = Storage.getFruits();
    private static final String FRUIT_NAME = "banana";
    private static final String NO_EXISTING_FRUIT_NAME = "apple";
    private static final int FRUIT_BALANCE_QUANTITY = 40;
    private static final int FRUIT_SUPPLY_QUANTITY = 20;
    private static final FruitSupply fruitSupply = new FruitSupply();

    @BeforeEach
    void setUp() {
        fruitsData.put(FRUIT_NAME, new Fruit(FRUIT_NAME, FRUIT_BALANCE_QUANTITY));
    }

    @Test
    void transactionHandler_fruitsDataNull_NotOk() {
        assertThrows(TransactionException.class, () -> {
            fruitSupply.transactionHandler(null, FRUIT_NAME, FRUIT_SUPPLY_QUANTITY);
        });
    }

    @Test
    void transactionHandler_fruitNameNull_NotOk() {
        assertThrows(TransactionException.class, () -> {
            fruitSupply.transactionHandler(fruitsData, null, FRUIT_SUPPLY_QUANTITY);
        });
    }

    @Test
    void transactionHandler_noFruit_Ok() {
        fruitSupply.transactionHandler(fruitsData, NO_EXISTING_FRUIT_NAME, FRUIT_SUPPLY_QUANTITY);
        int actualFruitsKindsQuantity = fruitsData.size();
        int fruitsKindsQuantityExpected = 2;
        assertEquals(actualFruitsKindsQuantity, fruitsKindsQuantityExpected);
    }

    @Test
    void transactionHandler_supply_Ok() {
        fruitSupply.transactionHandler(fruitsData, FRUIT_NAME, FRUIT_SUPPLY_QUANTITY);
        int fruitQuantityActual = fruitsData.get(FRUIT_NAME).getQuantity();
        int expectedFruitsKindsQuantity = FRUIT_BALANCE_QUANTITY + FRUIT_SUPPLY_QUANTITY;
        assertEquals(fruitQuantityActual, expectedFruitsKindsQuantity);
    }

    @AfterAll
    static void afterAll() {
        fruitsData.clear();
    }
}
