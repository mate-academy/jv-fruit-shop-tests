package core.basesyntax.dataprocessor;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.FruitDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {
    private static final String FRUIT_NAME = "apple";
    private BalanceHandler balanceHandler;

    @BeforeEach
    void setUp() {
        FruitDB.getInventory().clear();
        balanceHandler = new BalanceHandler();
    }

    @Test
    void apply_addsQuantityToFruit() {
        FruitDB.add(FRUIT_NAME, 10);
        balanceHandler.apply(FRUIT_NAME, 20);
        assertEquals(30, FruitDB.getInventory().get(FRUIT_NAME));
    }

    @Test
    void apply_newFruit_addsToInventory() {
        balanceHandler.apply(FRUIT_NAME, 15);
        assertEquals(15, FruitDB.getInventory().get(FRUIT_NAME));
    }

    @Test
    void apply_zeroQuantity_doesNotChangeInventory() {
        FruitDB.add(FRUIT_NAME, 10);
        balanceHandler.apply(FRUIT_NAME, 0);
        assertEquals(10, FruitDB.getInventory().get(FRUIT_NAME));
    }

    @Test
    void apply_negativeQuantity_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> balanceHandler.apply(FRUIT_NAME, -5)
        );
        assertEquals("Quantity cannot be negative", exception.getMessage());
    }
}
