package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.impl.BalanceOperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private OperationHandler balanceOperationHandler;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        StorageDao storageDao = new StorageDaoImpl();
        balanceOperationHandler = new BalanceOperationHandler(storageDao);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruitType("orange");
        fruitTransaction.setFruitQuantity(20);
    }

    @Test
    public void balanceOperation_Ok() {
        balanceOperationHandler.handle(fruitTransaction);
        Fruit expected = new Fruit("orange", 20);
        Fruit actual = Storage.fruits.get(0);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
