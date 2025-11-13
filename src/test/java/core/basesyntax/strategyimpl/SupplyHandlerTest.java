package core.basesyntax.strategyimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.FruitStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {

    private FruitStock fruitStock;
    private SupplyHandler supplyHandler;

    @BeforeEach
    void setUp() {
        FruitStock.stock.clear();
        fruitStock = new FruitStock();
        supplyHandler = new SupplyHandler(fruitStock);
    }

    @Test
    void handle_shouldAddNewFruit_whenFruitNotInStock() {
        supplyHandler.handle("apple", 10);
        assertEquals(10, fruitStock.getQuantity("apple"));
    }

    @Test
    void handle_shouldIncreaseQuantity_whenFruitAlreadyInStock() {
        fruitStock.add("banana", 5);
        supplyHandler.handle("banana", 15);
        assertEquals(20, fruitStock.getQuantity("banana"));
    }

    @Test
    void handle_shouldNotChangeStock_whenQuantityIsZero() {
        fruitStock.add("orange", 7);
        supplyHandler.handle("orange", 0);
        assertEquals(7, fruitStock.getQuantity("orange"));
    }

    @Test
    void handle_shouldAddMultipleFruits_correctly() {
        supplyHandler.handle("apple", 10);
        supplyHandler.handle("banana", 5);
        assertEquals(10, fruitStock.getQuantity("apple"));
        assertEquals(5, fruitStock.getQuantity("banana"));
    }
}
