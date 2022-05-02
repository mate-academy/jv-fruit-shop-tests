package core.basesyntax.operation;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balance;
    private static StorageDao storageDao;

    @BeforeClass
    public static void setUp() {
        storageDao = new StorageDaoImpl();
        balance = new BalanceOperationHandler(storageDao);
    }

    @Test(expected = RuntimeException.class)
    public void apply_nullFruit_NotOK() {
        balance.apply(null, 0);
    }

    @Test(expected = RuntimeException.class)
    public void apply_wrongQuantity_NotOK() {
        balance.apply(new Fruit("banana"), -1);
    }

    @Test
    public void apply_correctData_Ok() {
        Assert.assertTrue(balance.apply(new Fruit("banana"), 0));
    }
}
