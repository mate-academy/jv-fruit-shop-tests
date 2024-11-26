package core.basesyntax.dataprocessor;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.FruitDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnHandlerTest {

    private FruitDB fruitDB;
    private ReturnHandler returnHandler;

    @BeforeEach
    void setUp() {
        fruitDB = new FruitDB();
        returnHandler = new ReturnHandler(fruitDB);
        fruitDB.add("apple", 50);
        fruitDB.add("banana", 30);
    }

    @Test
    void apply_validReturn_increasesInventory() {
        returnHandler.apply("apple", 20);
        assertEquals(70, fruitDB.getInventory().get("apple"));
    }

    @Test
    void apply_newFruitAdded_increasesInventory() {
        returnHandler.apply("orange", 15);
        assertEquals(15, fruitDB.getInventory().get("orange"));
    }

    @Test
    void apply_zeroQuantity_doesNotChangeInventory() {
        returnHandler.apply("apple", 0);
        assertEquals(50, fruitDB.getInventory().get("apple"));
    }

    @Test
    void apply_negativeQuantity_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> returnHandler.apply("apple", -10));
    }
}
