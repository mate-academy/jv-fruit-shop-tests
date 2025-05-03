package core.basesyntax.service.operationwithfruits;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private StorageDao storageDao;
    private FruitTransaction fruitTransaction;
    private ReturnOperationHandler returnOperationHandler;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        returnOperationHandler = new ReturnOperationHandler(storageDao);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(fruitTransaction.convertOperation("r"));
        fruitTransaction.setFruit("banana");
        storageDao.update("banana",10);
    }

    @Test
    public void returnPositive_Ok() {
        Integer quantity = 10;
        fruitTransaction.setQuantity(quantity);
        returnOperationHandler.getOperation(fruitTransaction);
        Integer actual = Storage.fruitsStorage.get(fruitTransaction.getFruit());
        Integer expected = fruitTransaction.getQuantity() + quantity;
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void returnNegative_NotOk() {
        fruitTransaction.setQuantity(-15);
        returnOperationHandler.getOperation(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void returnMaxValue_Ok() {
        fruitTransaction.setQuantity(Integer.MAX_VALUE);
        returnOperationHandler.getOperation(fruitTransaction);
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();;
    }
}
