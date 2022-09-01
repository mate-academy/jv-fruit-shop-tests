package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    @Test
    public void returnOperationHandler_putValidData_ok() {
        FruitTransaction balanceTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 200);
        new BalanceOperationHandler().apply(balanceTransaction);
        FruitTransaction returnTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20);
        new ReturnOperationHandler().apply(returnTransaction);
        Integer expected = 220;
        Integer actual = Storage.storage.get(returnTransaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void returnOperationHandler_zeroQuantity_ok() {
        FruitTransaction balanceTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 200);
        new BalanceOperationHandler().apply(balanceTransaction);
        FruitTransaction returnTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 0);
        new ReturnOperationHandler().apply(returnTransaction);
        Integer expected = 200;
        Integer actual = Storage.storage.get(returnTransaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
