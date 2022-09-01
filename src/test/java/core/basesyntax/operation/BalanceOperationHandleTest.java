package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class BalanceOperationHandleTest {
    @Test
    public void balanceOperationHandler_putValidData_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 200);
        new BalanceOperationHandler().apply(transaction);
        Integer expected = transaction.getQuantity();
        Integer actual = Storage.storage.get(transaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
