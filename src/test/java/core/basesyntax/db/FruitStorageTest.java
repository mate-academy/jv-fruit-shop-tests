package core.basesyntax.db;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitStorageTest {
    private Map<String, Integer> fruits;

    @BeforeEach
    public void setUp() {
        fruits = new HashMap<>();
        FruitStorage.addFruit("banana", 78);
        FruitStorage.addFruit("apple", 14);
        FruitStorage.addFruit("watermelon", 39);
    }

    @Test
    void addAndGetOk() {
        fruits = FruitStorage.getFruits();
        Assert.assertEquals("Test failed! The size isn't correct. Expected 3 but was "
                + fruits.size(), 3, fruits.size());
        Integer firstActualValue = fruits.get("banana");
        Integer secondActualValue = fruits.get("apple");
        Integer thirdActualValue = fruits.get("watermelon");
        Assert.assertEquals("Test failed! HashMap expects to contain value 78 for key `banana`,"
                + " but was " + firstActualValue, Integer.valueOf(78), firstActualValue);
        Assert.assertEquals("Test failed! HashMap expects to contain value 14 for key `watermelon`,"
                + " but was " + secondActualValue, Integer.valueOf(14), secondActualValue);
        Assert.assertEquals("Test failed! HashMap expects to contain value 39 for key `thirdCar`,"
                + " but was " + thirdActualValue, Integer.valueOf(39), thirdActualValue);
    }

    @Test
    void getQuantityAndSetQuantityOk() {
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put("banana", 78);
        fruits.put("apple", 14);
        fruits.put("watermelon", 39);
        FruitStorage.setQuantity("banana", 100);
        FruitStorage.setQuantity("apple", 110);
        FruitStorage.updateFruitQuantity("watermelon", 120);

        Integer firstActualValue = FruitStorage.getQuantity("banana");
        Integer secondActualValue = FruitStorage.getQuantity("apple");
        Integer thirdActualValue = FruitStorage.getQuantity("watermelon");
        Assert.assertEquals("Test failed! "
                + "HashMap expects to contain value 100 for key `banana`,"
                + " but was " + firstActualValue, Integer.valueOf(100), firstActualValue);
        Assert.assertEquals("Test failed! HashMap expects to contain "
                + "value 110 for key `watermelon`,"
                + " but was " + secondActualValue, Integer.valueOf(110), secondActualValue);
        Assert.assertEquals("Test failed! HashMap expects to contain "
                + "value 120 for key `thirdCar`,"
                + " but was " + thirdActualValue, Integer.valueOf(120), thirdActualValue);
    }

    @Test
    void isFruitPresentOk() {
        Map<String, Integer> fruits = FruitStorage.getFruits();
        FruitStorage.addFruit("banana", 78);
        FruitStorage.addFruit("apple", 14);
        FruitStorage.addFruit("watermelon", 39);
        List<String> fruitAll = FruitStorage.getAllFruits();
        boolean actual = false;
        if (FruitStorage.isFruitPresent("apple")) {
            actual = true;
        }
        assertTrue(actual);
    }

    @Test
    void isFruitAllOk() {
        Map<String, Integer> fruits = FruitStorage.getFruits();
        fruits.put("banana", 78);
        fruits.put("apple", 14);
        fruits.put("watermelon", 39);
        List<String> fruitAll = FruitStorage.getAllFruits();
        boolean actual = false;
        if (fruitAll.contains("banana") && fruitAll.contains("apple")
                && fruitAll.contains("watermelon")) {
            actual = true;
        }
        assertTrue(actual);
    }

    @AfterEach
    public void afterEachTest() {
        fruits.clear();
    }
}
