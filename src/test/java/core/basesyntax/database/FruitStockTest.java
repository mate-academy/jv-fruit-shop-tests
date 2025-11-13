package core.basesyntax.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class FruitStockTest {

    @AfterEach
    void setUp() {
        FruitStock.stock.clear();
    }

    @Test
    void addFruit_successful_shouldIncreaseQuantity() {
        FruitStock fruitStock = new FruitStock();
        fruitStock.add("apple", 10);
        assertEquals(10, fruitStock.getQuantity("apple"));
    }

    @Test
    void subtractFruit_validQuantity_shouldDecreaseQuantity() {
        FruitStock fruitStock = new FruitStock();
        fruitStock.add("banana", 15);
        fruitStock.subtract("banana", 5);
        assertEquals(10, fruitStock.getQuantity("banana"));
    }

    @Test
    void subtractFruit_moreThanAvailable_shouldThrowException() {
        FruitStock fruitStock = new FruitStock();
        fruitStock.add("orange", 7);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fruitStock.subtract("orange", 10);
        });
        assertTrue(exception.getMessage().contains("Not enough"));
    }

    @Test
    void updateFruitQuantity_validFruit_shouldUpdateQuantity() {
        FruitStock fruitStock = new FruitStock();
        fruitStock.add("apple", 5);
        fruitStock.updateFruitQuantity("apple", 20);
        assertEquals(20, fruitStock.getQuantity("apple"));
    }

    @Test
    void getAll_whenModifiedCopy_shouldNotAffectOriginal() {
        FruitStock fruitStock = new FruitStock();
        fruitStock.add("chery", 2);
        Map<String, Integer> copy = fruitStock.getAll();
        copy.put("chery", 100);
        assertEquals(2, fruitStock.getQuantity("chery"));
    }
}
