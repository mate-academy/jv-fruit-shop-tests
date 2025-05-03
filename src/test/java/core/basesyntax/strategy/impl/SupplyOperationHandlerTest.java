package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {

    @Test
    public void handle_ValidTransaction_AddsToStorage_Ok() {
        SupplyOperationHandler supplyHandler = new SupplyOperationHandler();
        FruitStorage.FRUITS.put("Banana", 10);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "Banana", 5);

        supplyHandler.handle(transaction);

        assertEquals(15, FruitStorage.FRUITS.get("Banana"));
    }
}
