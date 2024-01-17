package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {
    private static FruitDao fruitDao;

    @BeforeAll
    public static void setUp() {
        fruitDao = new FruitDaoImpl();
    }

    @Test
    public void addFruit_validData_ok() {
        String fruitName = "banana";
        int quantity = 10;
        fruitDao.addFruit(fruitName, quantity);
        Map<String, Integer> fruits = FruitStorage.getFruits();
        assertEquals(quantity, fruits.get(fruitName), "Fruit is added wrong!");

        String fruitNameNull = null;
        fruitDao.addFruit(fruitNameNull, quantity);
        assertEquals(quantity, fruits.get(fruitNameNull),
                "Fruit with name null is added wrong!");

        fruitName = "apple";
        int quantityNegative = -2;
        fruitDao.addFruit(fruitName, quantityNegative);
        assertEquals(quantityNegative, fruits.get(fruitName),
                "Fruit with negative quantity is added wrong!");
    }

    @Test
    public void addFruit_repeatingData_ok() {
        String fruitName = "banana";
        int quantity = 10;
        fruitDao.addFruit(fruitName, quantity);
        int newQuantity = 15;
        fruitDao.addFruit(fruitName, newQuantity);
        Map<String, Integer> fruits = FruitStorage.getFruits();
        assertEquals(newQuantity, fruits.get(fruitName),
                "Same fruit is added wrong! Quantity should be replaced!");
    }

    @Test
    public void getAllFruits_ok() {
        Map<String, Integer> expected = FruitStorage.getFruits();
        Map<String, Integer> actual = fruitDao.getAllFruits();
        assertEquals(expected, actual,
                "Expected values from FruitStorage, but got wrong values!");
    }

    @Test
    public void getQuantityByFruitName_validName_ok() {
        String fruitName = "apple";
        int expectedQuantity = 15;
        FruitStorage.getFruits().put(fruitName, expectedQuantity);
        int actualQuantity = fruitDao.getQuantityByFruitName(fruitName);
        assertEquals(expectedQuantity, actualQuantity,
                "Expected quantity = " + expectedQuantity
                + ", but was " + actualQuantity);

        String fruitNameNull = null;
        FruitStorage.getFruits().put(fruitNameNull, expectedQuantity);
        actualQuantity = fruitDao.getQuantityByFruitName(fruitNameNull);
        assertEquals(expectedQuantity, actualQuantity,
                "Expected quantity = " + expectedQuantity + ", but was " + actualQuantity);
    }

    @AfterEach
    public void afterEachTest() {
        FruitStorage.getFruits().clear();
    }
}
