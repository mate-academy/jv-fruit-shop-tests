package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.handler.impl.ReturnHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnHandlerTest {
    private ReturnHandler returnHandler;

    @BeforeEach
    public void setUp() {
        Storage.storage.clear();
        returnHandler = new ReturnHandler();
    }

    @Test
    public void returnHandler_ValidTransaction_Ok() {
        Storage.storage.put("apple", 10);
        FruitTransaction fruitTransaction
                = new FruitTransaction(Operation.RETURN,
                "apple", 5);
        returnHandler.handle(fruitTransaction);
        assertEquals(15, Storage.storage.get("apple"));
    }

    @Test
    public void returnHandler_NullTransaction_notOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.RETURN,
                        null, 5);
        NullPointerException exception =
                Assertions.assertThrows(NullPointerException.class, ()
                        -> returnHandler.handle(fruitTransaction));
        assertEquals("FruitTransaction cannot be null", exception.getMessage());
    }

    @Test
    public void returnHandler_NegativeQuantity_notOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.RETURN,
                        "apple", -5);
        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class, ()
                        -> returnHandler.handle(fruitTransaction));
        assertEquals("FruitTransaction quantity cannot be negative",
                exception.getMessage());
    }
}
