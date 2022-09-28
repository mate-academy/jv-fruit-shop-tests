package core.basesyntax.service.operationwithfruits;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private StorageDao storageDao;
    private FruitTransaction fruitTransaction;
    private SupplyOperationHandler supplyOperationHandler;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        supplyOperationHandler = new SupplyOperationHandler(storageDao);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(fruitTransaction.convertOperation("s"));
        fruitTransaction.setFruit("banana");
        storageDao.update("banana",10);
    }

    @Test
    public void supplyPositive_Ok() {
        Integer quantity = 10;
        fruitTransaction.setQuantity(quantity);
        supplyOperationHandler.getOperation(fruitTransaction);
        Integer actual = Storage.fruitsStorage.get(fruitTransaction.getFruit());
        Integer expected = fruitTransaction.getQuantity() + quantity;
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void supplyNegative_NotOk() {
        fruitTransaction.setQuantity(-15);
        supplyOperationHandler.getOperation(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void supplyMaxValue_Ok() {
        fruitTransaction.setQuantity(Integer.MAX_VALUE);
        supplyOperationHandler.getOperation(fruitTransaction);
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();;
    }
}
