package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.InvalidAmountException;
import core.basesyntax.model.product.Fruit;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationDecreaseHandlerTest {
    private static StorageDao storageDao;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        operationHandler = new OperationDecreaseHandler(storageDao);
    }

    @Test
    public void apply_isOk() {
        storageDao.add(new Fruit("apple"), 20);
        int expected = 10;
        int actual = operationHandler.apply(new Fruit("apple"), 10);
        assertEquals(expected, actual);
    }

    @Test(expected = InvalidAmountException.class)
    public void apply_FromEmptyStorage_notOk() {
        operationHandler.apply(new Fruit("apple"), 50);
    }

    @Test(expected = InvalidAmountException.class)
    public void apply_AmountLess_notOk() {
        storageDao.add(new Fruit("apple"), 20);
        operationHandler.apply(new Fruit("apple"),30);
    }

    @After
    public void tearDown() {
        storageDao.getAll().clear();
    }
}
