package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {

    private final OperationHandler operationHandler = new BalanceOperationHandler();
    private FruitTransaction fruitTransaction;

    @Test
    public void handle_CorrectDate_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);
        operationHandler.handle(fruitTransaction);
        int expectedFruitQuantity = 100;
        int actualQuantity = Storage.getStorageMap().get("banana");
        Assert.assertEquals(expectedFruitQuantity, actualQuantity);
    }
}
