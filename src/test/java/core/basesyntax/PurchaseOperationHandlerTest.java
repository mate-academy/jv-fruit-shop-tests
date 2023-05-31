package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.impl.PurchaseOperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private OperationHandler purchaseOperationHandler;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        StorageDao storageDao = new StorageDaoImpl();
        purchaseOperationHandler = new PurchaseOperationHandler(storageDao);
        fruitTransaction = new FruitTransaction();
    }

    @Test
    public void purchaseWithNoSuchFruitInStorage_Ok() {
        try {
            purchaseOperationHandler.handle(fruitTransaction);
        } catch (RuntimeException e) {
            System.out.println("Exception: " + e);
        }
    }

    @Test
    public void purchaseWithFruitInStorage_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruitType("orange");
        fruitTransaction.setFruitQuantity(20);
        Storage.fruits.add(new Fruit("orange", 30));
        purchaseOperationHandler.handle(fruitTransaction);
        Fruit actual = Storage.fruits.get(0);
        Fruit expected = new Fruit("orange", 10);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
