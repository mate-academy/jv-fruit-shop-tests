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
    public void getFruitStorage_putAndGetFromStorage_Ok() {
        Storage.getFruitStorage().put(FRUIT, FIRST_QUANTITY);

        assertEquals("Test failed! The storage should return the value \""
                        + FIRST_QUANTITY + "\" with the key \"" + FRUIT + "\"",
                FIRST_QUANTITY, Storage.getFruitStorage()
                        .getOrDefault(FRUIT, DEFAULT_QUANTITY).intValue());
    }

    @Test
    public void getFruitStorage_putAndUpdateExistingFruit_Ok() {
        Storage.getFruitStorage().put(FRUIT, FIRST_QUANTITY);
        Storage.getFruitStorage().merge(FRUIT, SECOND_QUANTITY, Integer::sum);

        assertEquals("Test failed! The value in the storage should have been updated to be: "
                        + (FIRST_QUANTITY + SECOND_QUANTITY),
                FIRST_QUANTITY + SECOND_QUANTITY,
                Storage.getFruitStorage().getOrDefault(FRUIT, DEFAULT_QUANTITY).intValue());
    }
}
