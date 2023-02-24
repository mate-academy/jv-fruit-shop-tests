package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationImplTest {
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static FruitTransaction transactionApple10;
    private static FruitTransaction transactionApple20;
    private static FruitTransaction transactionBanana100;

    @BeforeClass
    public static void beforeAll() {
        transactionApple10 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                new Fruit("apple"),
                10);

        transactionApple20 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                new Fruit("apple"),
                20);

        transactionBanana100 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                new Fruit("banana"),
                100);
    }

    @After
    public void afterEachTest() {
        Storage.getStorage().clear();
    }

    @Test
    public void registerTransaction_DifferentFruits_Ok() {
        OperationHandler balanceOperation = new BalanceOperationImpl(storageDao);
        int expectedQty = transactionApple10.getQuantity();
        balanceOperation.registerTransaction(transactionApple10);
        int actualQty = Storage.getStorage().get(transactionApple10.getFruit());
        assertEquals(1, Storage.getStorage().size());
        assertEquals(expectedQty, actualQty);

        expectedQty = transactionBanana100.getQuantity();
        balanceOperation.registerTransaction(transactionBanana100);
        actualQty = Storage.getStorage().get(transactionBanana100.getFruit());
        assertEquals(2, Storage.getStorage().size());
        assertEquals(expectedQty, actualQty);
    }

    @Test
    public void registerTransaction_SameFruits_Ok() {
        OperationHandler balanceOperation = new BalanceOperationImpl(storageDao);
        int expectedQty = transactionApple10.getQuantity();
        balanceOperation.registerTransaction(transactionApple10);
        int actualQty = Storage.getStorage().get(transactionApple10.getFruit());
        assertEquals(1, Storage.getStorage().size());
        assertEquals(expectedQty, actualQty);

        expectedQty = transactionApple20.getQuantity();
        balanceOperation.registerTransaction(transactionApple20);
        actualQty = Storage.getStorage().get(transactionApple20.getFruit());
        assertEquals(1, Storage.getStorage().size());
        assertEquals(expectedQty, actualQty);
    }
}
