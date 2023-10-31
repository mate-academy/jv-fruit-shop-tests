package core.basesyntax.db;

import static org.junit.Assert.assertEquals;

import core.basesyntax.Constants;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class FruitStorageTest {
    private static final int INITIAL_QUANTITY = 10;
    private static final int UPDATED_QUANTITY = 5;
    private static final int ZERO_QUANTITY = 0;
    private static final int EXPECTED_ARRAYLIST_SIZE = 2;

    private FruitStorage fruitStorage;

    @Before
    public void setUp() {
        fruitStorage = new FruitStorage();
    }

    @Test
    public void updateQuantity_AddNewFruit_QuantitiesUpdated() {
        fruitStorage.updateQuantity(Constants.APPLE, INITIAL_QUANTITY);
        fruitStorage.updateQuantity(Constants.ORANGE, UPDATED_QUANTITY);

        assertEquals(INITIAL_QUANTITY, getQuantityForFruit(Constants.APPLE));
        assertEquals(UPDATED_QUANTITY, getQuantityForFruit(Constants.ORANGE));
    }

    @Test
    public void updateQuantity_UpdateExistingFruit_QuantitiesUpdated() {
        fruitStorage.updateQuantity(Constants.APPLE, INITIAL_QUANTITY);
        fruitStorage.updateQuantity(Constants.APPLE, UPDATED_QUANTITY);

        assertEquals(INITIAL_QUANTITY + UPDATED_QUANTITY, getQuantityForFruit(Constants.APPLE));
    }

    @Test
    public void updateQuantity_UpdateToZero_QuantitiesUnchanged() {
        fruitStorage.updateQuantity(Constants.APPLE, INITIAL_QUANTITY);
        fruitStorage.updateQuantity(Constants.APPLE, ZERO_QUANTITY);

        assertEquals(INITIAL_QUANTITY, getQuantityForFruit(Constants.APPLE));
    }

    @Test
    public void getFruitQuantities_EmptyStorage_ReturnsEmptyMap() {
        Map<String, Integer> quantities = fruitStorage.getFruitQuantities();

        assertEquals(ZERO_QUANTITY, quantities.size());
    }

    @Test
    public void getFruitQuantities_NonEmptyStorage_ReturnsCorrectQuantities() {
        fruitStorage.updateQuantity(Constants.APPLE, INITIAL_QUANTITY);
        fruitStorage.updateQuantity(Constants.ORANGE, UPDATED_QUANTITY);

        Map<String, Integer> quantities = fruitStorage.getFruitQuantities();

        assertEquals(EXPECTED_ARRAYLIST_SIZE, quantities.size());
        assertEquals(INITIAL_QUANTITY, getQuantityForFruit(Constants.APPLE));
        assertEquals(UPDATED_QUANTITY, getQuantityForFruit(Constants.ORANGE));
    }

    private int getQuantityForFruit(String fruit) {
        return fruitStorage.getFruitQuantities().getOrDefault(fruit, ZERO_QUANTITY);
    }
}
