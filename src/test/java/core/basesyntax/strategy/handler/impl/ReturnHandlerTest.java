package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnHandlerTest {

    private ReturnHandler returnHandler;

    @BeforeEach
    public void setUp() {
        returnHandler = new ReturnHandler();
    }

    @AfterEach
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void returnHandler_ValidTransaction_Ok() {
        Storage.storage.put("apple", 10);
        FruitTransaction fruitTransaction
                = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "apple", 5);
        returnHandler.handle(fruitTransaction);
        assertEquals(15, Storage.storage.get("apple"));
    }

    @Test
    public void returnHandler_NullTransaction_NotOk() {
        assertThrows(NullPointerException.class, ()
                -> returnHandler.handle(new FruitTransaction(FruitTransaction.Operation.RETURN,
                null, 5)));
    }

    @Test
    public void returnHandler_NegativeQuantity_NotOk() {
        assertThrows(IllegalArgumentException.class, ()
                -> returnHandler.handle(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "apple", -5)));
    }
}
