package core.basesyntax.dataprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.FruitDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {

    private BalanceHandler balanceHandler;
    private FruitDB fruitDB;

    @BeforeEach
    void setUp() {
        fruitDB = FruitDB.getInstance();
        fruitDB.getInventory().clear();
        balanceHandler = new BalanceHandler();
    }

    @Test
    void apply_addsQuantityToFruit() {
        fruitDB.add("apple", 10);
        balanceHandler.apply("apple", 20);

        assertEquals(30, fruitDB.getInventory().get("apple"));
    }

    @Test
    void apply_validFruitAndQuantity_updatesInventory() {
        String fruit = "apple";
        int quantity = 50;
        balanceHandler.apply(fruit, quantity);
        assertEquals(50, fruitDB.getInventory().get(fruit).intValue());
    }

    @Test
    void apply_existingFruit_updatesInventoryCorrectly() {
        String fruit = "banana";
        fruitDB.add(fruit, 30);
        balanceHandler.apply(fruit, 20);
        assertEquals(50, fruitDB.getInventory().get(fruit).intValue());
    }

    @Test
    void apply_zeroQuantity_doesNotChangeInventory() {
        String fruit = "orange";
        int quantity = 0;
        balanceHandler.apply(fruit, quantity);
        assertEquals(0, fruitDB.getInventory().getOrDefault(fruit, 0).intValue());
    }
}
