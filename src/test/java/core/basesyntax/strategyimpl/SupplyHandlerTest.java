package core.basesyntax.strategyimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.FruitStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyHandlerTest {
    private FruitStock fruitStock;
    private SupplyHandler supplyHandler;

    @BeforeEach
    void setUp() {
        fruitStock = new FruitStock();
        supplyHandler = new SupplyHandler(fruitStock);
    }

    @Test
    void handle_correctlyAddsFruit() {
        supplyHandler.handle("apple", 10);
        assertEquals(10, fruitStock.getQuantity("apple"));
    }

    @Test
    void handle_doesNotChangeQuantity_whenAddingZero() {
        fruitStock.add("apple", 10);
        supplyHandler.handle("apple", 0);
        assertEquals(10, fruitStock.getQuantity("apple"));
    }

    @Test
    void handle_doesNotChangeQuantity_whenAddingNegativeAmount() {
        fruitStock.add("apple", 10);
        supplyHandler.handle("apple", -5);
        assertEquals(10, fruitStock.getQuantity("apple"));
    }

}
