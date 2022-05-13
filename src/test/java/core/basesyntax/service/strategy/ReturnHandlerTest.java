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

public class ReturnHandlerTest {
    private static OperationHandler operationHandler;
    private static StorageDao storageDao;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        operationHandler = new ReturnHandler(storageDao);
    }

    @Test
    public void return_correct_Ok() {
        Fruit apple = new Fruit("apple");
        Storage.dataBase.put(apple, 12);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, apple, 32);
        operationHandler.apply(fruitTransaction);
        int expected = 44;
        int actual = Storage.dataBase.get(apple);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.dataBase.clear();
    }
}
