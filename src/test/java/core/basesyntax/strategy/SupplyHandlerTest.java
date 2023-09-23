package core.basesyntax.strategy;

import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.handler.impl.SupplyHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyHandlerTest {
    private SupplyHandler supplyHandler;

    @BeforeEach
    public void setUp() {
        Storage.storage.clear();
        supplyHandler = new SupplyHandler();
    }

    @Test
    public void supplyHandler_ValidTransaction_Ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.SUPPLY,
                        "apple", 10);
        supplyHandler.handle(fruitTransaction);
        int updateQuantity = Storage.storage.get("apple");
        Assertions.assertEquals(10, updateQuantity, "Expected the quantity of "
                + "apples to be 10.");
    }

    @Test
    public void supplyHandler_InvalidTransactionWithNegativeQuantity_notOk() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(Operation.SUPPLY,
                "banana", -5);
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, ()
                        -> supplyHandler.handle(fruitTransaction));
        Assertions.assertEquals("Transaction quantity cannot be negative", exception.getMessage());
    }

    @Test
    public void supplyHandler_NullTransaction_notOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.SUPPLY,
                        null, 5);
        NullPointerException exception =
                assertThrows(NullPointerException.class, ()
                        -> supplyHandler.handle(fruitTransaction));
        Assertions.assertEquals("FruitTransaction cannot be null", exception.getMessage());
    }
}
