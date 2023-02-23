package core.basesyntax.database;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

public class StorageTest {
    private static final String FRUIT = "banana";
    private static final int FIRST_QUANTITY = 10;
    private static final int SECOND_QUANTITY = 5;
    private static final int DEFAULT_QUANTITY = 0;

    @After
    public void tearDown() {
        Storage.getFruitStorage().clear();
    }

    @Test
    public void getFruitStorage_get() {
        //arrange
        String errorMessage = String.format("Wrong value for the key \"%s\":", FRUIT);
        int expectedQuantity = FIRST_QUANTITY;
        Storage.getFruitStorage().put(FRUIT, expectedQuantity);

        //act
        int actualQuantity = Storage.getFruitStorage().getOrDefault(FRUIT, DEFAULT_QUANTITY);

        //assert
        assertEquals(errorMessage, expectedQuantity, actualQuantity);
    }

    @Test
    public void getFruitStorage_updateExistent() {
        //arrange
        int expectedQuantity = FIRST_QUANTITY + SECOND_QUANTITY;
        Storage.getFruitStorage().put(FRUIT, FIRST_QUANTITY);

        //act
        Storage.getFruitStorage().merge(FRUIT, SECOND_QUANTITY, Integer::sum);
        int actualQuantity = Storage.getFruitStorage().getOrDefault(FRUIT, DEFAULT_QUANTITY);

        //assert
        assertEquals("Wrong value in the storage after updating:",
                expectedQuantity, actualQuantity);
    }

    @Test
    public void getFruitStorage_updateNonExistent() {
        //arrange
        int expectedQuantity = SECOND_QUANTITY;

        //act
        Storage.getFruitStorage().merge(FRUIT, expectedQuantity, Integer::sum);
        int actualQuantity = Storage.getFruitStorage().getOrDefault(FRUIT, DEFAULT_QUANTITY);

        //assert
        assertEquals("Wrong value in the storage after updating:",
                expectedQuantity, actualQuantity);
    }
}
