package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    @Test
    public void supply_putValidData_ok() {
        FruitTransaction balanceTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 200);
        new BalanceOperationHandler().apply(balanceTransaction);
        FruitTransaction supplyTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20);
        new SupplyOperationHandler().apply(supplyTransaction);
        Integer expected = 220;
        Integer actual = Storage.storage.get(supplyTransaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
