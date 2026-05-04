package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {
    private final OperationHandler operationHandler = new ReturnOperationHandler();

    @AfterEach
    void clearStorage() {
        Storage.getStorage().clear();
    }

    @Test
    void apply_returnHandler_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 10);
        operationHandler.apply(transaction);

        assertEquals(10, Storage.getStorage().get("banana"));
    }
}
