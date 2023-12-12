package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {

    @Test
    public void handle_ValidTransaction_ReturnsToStorage_Ok() {
        ReturnOperationHandler returnHandler = new ReturnOperationHandler();
        FruitStorage.FRUITS.put("Apple", 10);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "Apple", 5);

        returnHandler.handle(transaction);

        assertEquals(15, FruitStorage.FRUITS.get("Apple"));
    }

    @Test
    public void handle_ValidTransaction_AddsToExistingQuantity_Ok() {
        ReturnOperationHandler returnHandler = new ReturnOperationHandler();
        FruitStorage.FRUITS.put("Orange", 5);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "Orange", 8);

        returnHandler.handle(transaction);

        assertEquals(13, FruitStorage.FRUITS.get("Orange"));
    }
}
