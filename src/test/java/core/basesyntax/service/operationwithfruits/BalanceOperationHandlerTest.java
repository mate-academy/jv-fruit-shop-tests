package core.basesyntax.service.operationwithfruits;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private StorageDao storageDao;
    private FruitTransaction fruitTransaction;
    private BalanceOperationHandler balanceOperationHandler;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        balanceOperationHandler = new BalanceOperationHandler(storageDao);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(fruitTransaction.convertOperation("b"));
        fruitTransaction.setFruit("banana");
    }

    @Test
    public void balanceSetPositive_Ok() {
        fruitTransaction.setQuantity(10);
        balanceOperationHandler.getOperation(fruitTransaction);
        Integer actual = Storage.fruitsStorage.get(fruitTransaction.getFruit());
        Integer expected = fruitTransaction.getQuantity();
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void balanceSetNegative_NotOk() {
        fruitTransaction.setQuantity(-10);
        balanceOperationHandler.getOperation(fruitTransaction);
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();;
    }
}
