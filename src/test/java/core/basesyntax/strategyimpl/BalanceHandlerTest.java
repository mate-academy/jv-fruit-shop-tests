package core.basesyntax.strategyimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.FruitStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {
    private FruitStock fruitStock;
    private BalanceHandler balanceHandler;

    @BeforeEach
    void setUp() {
        fruitStock = new FruitStock();
        balanceHandler = new BalanceHandler(fruitStock);
    }

    @Test
    void handle_correctlySetsBalance() {
        fruitStock.add("apple", 0);
        balanceHandler.handle("apple", 10);
        assertEquals(10, fruitStock.getQuantity("apple"));
    }

    @Test
    void handle_existingFruit_updatesBalance() {
        fruitStock.add("apple", 5);
        balanceHandler.handle("apple", 10);
        assertEquals(10, fruitStock.getQuantity("apple"));
    }

    @Test
    void handle_multipleFruits_setsCorrectBalance() {
        // Додаємо фрукти в інвентар
        fruitStock.add("apple", 0);
        fruitStock.add("banana", 0);
        balanceHandler.handle("apple", 10);
        balanceHandler.handle("banana", 15);
        assertEquals(10, fruitStock.getQuantity("apple"));
        assertEquals(15, fruitStock.getQuantity("banana"));
    }
}
