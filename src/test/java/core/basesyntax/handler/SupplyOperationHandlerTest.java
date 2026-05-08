package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {
    private final OperationHandler operationHandler = new SupplyOperationHandler();

    @AfterEach
    void clearStorage() {
        Storage.getStorage().clear();
    }

    @Test
    void apply_supplyHandler_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 100);

        operationHandler.apply(transaction);

        assertEquals(100, Storage.getStorage().get("apple"));
    }
}
