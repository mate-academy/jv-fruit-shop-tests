package core.basesyntax.strategy.impl;

import static junit.framework.TestCase.assertTrue;
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

        int updatedBalance = FruitStorage.FRUITS.get("Apple");
        assertTrue(updatedBalance >= 0);
        assertEquals(15, updatedBalance);
    }
}
