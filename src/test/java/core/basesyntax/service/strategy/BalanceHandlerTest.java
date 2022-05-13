package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceHandlerTest {
    private static OperationHandler operationHandler;
    private static StorageDao storageDao;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        operationHandler = new BalanceHandler(storageDao);
    }

    @Test
    public void balance_correct_Ok() {
        Fruit apple = new Fruit("apple");
        Storage.dataBase.put(apple, 12);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, apple, 29);
        operationHandler.apply(fruitTransaction);
        int expected = 41;
        int actual = Storage.dataBase.get(apple);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.dataBase.clear();
    }
}
