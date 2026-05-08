package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private final OperationHandler operationHandler = new BalanceOperationHandler();

    @AfterEach
    void clearStorage() {
        Storage.getStorage().clear();
    }

    @Test
    void apply_balanceHandler_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20);

        operationHandler.apply(transaction);

        assertEquals(20, Storage.getStorage().get("banana"));
    }
}
