package core.basesyntax.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitStockTest {
    private FruitStock fruitStock;

    @BeforeEach
    void setUp() {
        fruitStock = new FruitStock();
    }

    @Test
    void addFruit_ok() {
        fruitStock.add("apple", 10);
        assertEquals(10, fruitStock.getQuantity("apple"));
    }

    @Test
    void subtractFruit_ok() {
        fruitStock.add("banana", 20);
        fruitStock.subtract("banana", 5);
        assertEquals(15, fruitStock.getQuantity("banana"));
    }

    @Test
    void subtractFruit_notEnough_shouldThrow() {
        fruitStock.add("orange", 2);
        Exception exception = assertThrows(RuntimeException.class,
                () -> fruitStock.subtract("orange", 5));
        assertEquals("Not enough orange in stock.", exception.getMessage());
    }

}
