package core.basesyntax.db;

import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitStorageTest {
    private FruitStorage fruitStorage;

    @BeforeEach
    void setUp() {
        fruitStorage = new FruitStorage();
    }

    @Test
    void check_fruitStorage_Empty_OK() {
        Assertions.assertEquals(0, fruitStorage.getData().size());
    }

    @Test
    void check_fruitStorage_Update_OK() {
        fruitStorage.updateFruitQuantity("banana", 50);
        Set<Map.Entry<String, Integer>> storageData = fruitStorage.getData();
        for (Map.Entry<String, Integer> testData : storageData) {
            Assertions.assertEquals(50, testData.getValue());
            Assertions.assertEquals("banana", testData.getKey());
        }
        fruitStorage.updateFruitQuantity("banana", 50);
        for (Map.Entry<String, Integer> testData : storageData) {
            Assertions.assertEquals(100, testData.getValue());
            Assertions.assertEquals("banana", testData.getKey());
        }

    }

}
