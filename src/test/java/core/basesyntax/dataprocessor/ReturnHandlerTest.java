package core.basesyntax.dataprocessor;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.FruitDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnHandlerTest {
    private ReturnHandler returnHandler;

    @BeforeEach
    void setUp() {
        FruitDB.getInventory().clear();
        returnHandler = new ReturnHandler();
        FruitDB.add("apple", 50);
        FruitDB.add("banana", 30);
    }

    @Test
    void apply_validReturn_increasesInventory() {
        returnHandler.apply("apple", 20);
        assertEquals(70, FruitDB.getInventory().get("apple").intValue());
    }

    @Test
    void apply_newFruitAdded_increasesInventory() {
        returnHandler.apply("orange", 15);
        assertEquals(15, FruitDB.getInventory().get("orange").intValue());
    }

    @Test
    void apply_zeroQuantity_doesNotChangeInventory() {
        returnHandler.apply("apple", 0);
        assertEquals(50, FruitDB.getInventory().get("apple").intValue());
    }

    @Test
    void apply_negativeQuantity_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> returnHandler.apply("apple", -10)
        );
        assertEquals("Quantity cannot be negative", exception.getMessage());
    }
}
