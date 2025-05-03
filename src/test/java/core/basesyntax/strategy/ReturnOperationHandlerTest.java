package core.basesyntax.strategy;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnHandler;
    private static FruitStorageDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitStorageDaoImpl();
        returnHandler = new ReturnOperationHandler(fruitDao);
    }

    @Before
    public void setUp() {
        fruitDao.add(new Fruit("apple"), 50);
    }

    @Test
    public void apply_correctInput_ok() {
        int expected = 100;
        TransactionDto transaction = new TransactionDto("r", "apple", 50);
        returnHandler.apply(transaction);
        int actual = Storage.storage.get(new Fruit("apple"));
        Assert.assertEquals(actual, expected);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
