package core.basesyntax.dataprocessor;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.FruitDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnHandlerTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private ReturnHandler returnHandler;

    @BeforeEach
    void setUp() {
        FruitDB.getInventory().clear();
        returnHandler = new ReturnHandler();
        FruitDB.add(APPLE, 50);
    }

    @Test
    void apply_validReturn_increasesInventory() {
        returnHandler.apply(APPLE, 20);
        assertEquals(70, FruitDB.getInventory().get(APPLE).intValue());
    }

    @Test
    void apply_newFruitAdded_increasesInventory() {
        returnHandler.apply(BANANA, 15);
        assertEquals(15, FruitDB.getInventory().get(BANANA).intValue());
    }

    @Test
    void apply_zeroQuantity_doesNotChangeInventory() {
        returnHandler.apply(APPLE, 0);
        assertEquals(50, FruitDB.getInventory().get(APPLE).intValue());
    }

    @Test
    void apply_negativeQuantity_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> returnHandler.apply(APPLE, -10)
        );
        assertEquals("Quantity cannot be negative", exception.getMessage());
    }
}
