package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.impl.SupplyOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private final OperationService supplyOperation = new SupplyOperation();

    @Test
    void processReturnOperation_ok() {
        Storage.getStorage().put("apple", 100);
        FruitTransaction fruitTransaction = new FruitTransaction(
                Operation.SUPPLY, "apple", 15);
        assertDoesNotThrow(() -> supplyOperation.doOperation(fruitTransaction));
        int actual = Storage.getStorage().get("apple");
        assertEquals(115, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }
}
