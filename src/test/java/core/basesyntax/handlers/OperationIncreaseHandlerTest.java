package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.product.Fruit;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationIncreaseHandlerTest {
    private static StorageDao storageDao;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        operationHandler = new OperationIncreaseHandler(storageDao);
    }

    @After
    public void tearDown() {
        storageDao.getAll().clear();
    }

    @Test
    public void increaseApplyInEmptyStorage_isOk() {
        int expected = 20;
        int actual = operationHandler.apply(new Fruit("apple"), 20);
        assertEquals(expected, actual);
    }

    @Test
    public void increaseApplyWithOldAmount_isOk() {
        storageDao.add(new Fruit("apple"), 20);
        int expected = 50;
        int actual = operationHandler.apply(new Fruit("apple"),30);
        assertEquals(expected, actual);
    }
}
