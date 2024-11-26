package core.basesyntax.dataprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyHandlerTest {

    private FruitDB fruitDB;
    private SupplyHandler supplyHandler;

    @BeforeEach
    void setUp() {
        fruitDB = new FruitDB();
        supplyHandler = new SupplyHandler(fruitDB);
        fruitDB.add("apple", 50);
        fruitDB.add("banana", 30);
    }

    @Test
    void apply_validSupply_increasesInventory() {
        supplyHandler.apply("apple", 20);
        assertEquals(70, fruitDB.getInventory().get("apple"));
    }

    @Test
    void apply_newFruitAdded_increasesInventory() {
        supplyHandler.apply("orange", 15);
        assertEquals(15, fruitDB.getInventory().get("orange"));
    }

    @Test
    void apply_zeroQuantity_doesNotChangeInventory() {
        supplyHandler.apply("apple", 0);
        assertEquals(50, fruitDB.getInventory().get("apple"));
    }

    @Test
    void apply_negativeQuantity_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> supplyHandler.apply("apple", -10));
    }
}
