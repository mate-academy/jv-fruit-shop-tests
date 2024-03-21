package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Operation;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private final OperationHandler balance = new BalanceOperationHandler();
    private final FruitTransaction testTransaction = new FruitTransaction(
            Operation.BALANCE, "banana", 100);

    @Test
    void balanceOperationHandler_ApplyOK_True() {
        balance.apply(testTransaction);
        assertTrue(Storage.dataBase.containsKey("banana"));
        assertTrue(Storage.dataBase.containsValue(100));
    }

    @Test
    void balanceOperationHandler_IsApplicableOK_True() {
        assertTrue(balance.isAplicable(testTransaction));
    }
}
