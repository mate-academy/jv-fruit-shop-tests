package core.basesyntax.service.operationwithfruits;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private StorageDao storageDao;
    private FruitTransaction fruitTransaction;
    private PurchaseOperationHandler purchaseOperationHandler;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        purchaseOperationHandler = new PurchaseOperationHandler(storageDao);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(fruitTransaction.convertOperation("p"));
        fruitTransaction.setFruit("banana");
        storageDao.update("banana",20);
    }

    @Test
    public void purchasePositive_Ok() {
        Integer quantity = 10;
        fruitTransaction.setQuantity(quantity);
        Integer actual = Storage.fruitsStorage.get(fruitTransaction.getFruit());
        purchaseOperationHandler.getOperation(fruitTransaction);
        Integer expected = fruitTransaction.getQuantity() + quantity;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseMaxValue_Ok() {
        fruitTransaction.setQuantity(Integer.MAX_VALUE);
        purchaseOperationHandler.getOperation(fruitTransaction);
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();;
    }
}
