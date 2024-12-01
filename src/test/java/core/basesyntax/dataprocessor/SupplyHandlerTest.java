package core.basesyntax.dataprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyHandlerTest {

    private SupplyHandler supplyHandler;

    @BeforeEach
    void setUp() {
        FruitDB.getInstance().getInventory().clear();
        supplyHandler = new SupplyHandler();
        FruitDB.getInstance().add("apple", 50);
        FruitDB.getInstance().add("banana", 30);
    }

    @Test
    void apply_validSupply_increasesInventory() {
        supplyHandler.apply("apple", 20);
        assertEquals(70, FruitDB.getInstance().getInventory().get("apple").intValue());
    }

    @Test
    void apply_newFruitAdded_increasesInventory() {
        supplyHandler.apply("orange", 15);
        assertEquals(15, FruitDB.getInstance().getInventory().get("orange").intValue());
    }

    @Test
    void apply_zeroQuantity_doesNotChangeInventory() {
        supplyHandler.apply("apple", 0);
        assertEquals(50, FruitDB.getInstance().getInventory().get("apple").intValue());
    }

    @Test
    void apply_negativeQuantity_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> supplyHandler.apply("apple", -10));
    }
}
