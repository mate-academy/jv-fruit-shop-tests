package core.basesyntax.database;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitStockTest {

    @BeforeEach
    void setUp() {
        FruitStock.stock.clear();
    }

    @Test
    void add() {
        FruitStock.stock.clear();
        FruitStock fruitStock = new FruitStock();
        fruitStock.add("apple", 10);
        Assertions.assertEquals(10, fruitStock.getQuantity("apple"));
    }


    @Test
    void subtract() {
        FruitStock fruitStock = new FruitStock();
        fruitStock.add("banana", 15);
        fruitStock.subtract("banana", 5);
        Assertions.assertEquals(10, fruitStock.getQuantity("banana"));
    }

    @Test
    void subtract_moreThanAvailable_shouldThrowException() {
        FruitStock fruitStock = new FruitStock();
        fruitStock.add("orange", 7);
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            fruitStock.subtract("orange", 10);
        });
        Assertions.assertTrue(exception.getMessage().contains("Not enough"));
    }

    @Test
    void updateFruitQuantity_validFruit_updatesCorrectly() {
        FruitStock fruitStock = new FruitStock();
        fruitStock.add("apple", 5);
        fruitStock.updateFruitQuantity("apple", 20);
        Assertions.assertEquals(20, fruitStock.getQuantity("apple"));
    }

    @Test
    void getAll_returnsCopyNotAffectingOriginal() {
        FruitStock fruitStock = new FruitStock();
        fruitStock.add("chery", 2);
        Map<String, Integer> copy = fruitStock.getAll();
        copy.put("chery", 100);
        Assertions.assertEquals(2, fruitStock.getQuantity("chery"));
    }
}
