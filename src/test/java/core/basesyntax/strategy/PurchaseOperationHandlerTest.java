package core.basesyntax.strategy;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseHandler;
    private static FruitStorageDaoImpl fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitStorageDaoImpl();
        purchaseHandler = new PurchaseOperationHandler(fruitDao);
    }

    @Before
    public void setUp() {
        fruitDao.add(new Fruit("banana"), 10);
    }

    @Test
    public void purchase_correctInput_ok() {
        int expected = 5;
        TransactionDto transaction = new TransactionDto("p", "banana", 5);
        purchaseHandler.apply(transaction);
        int actual = Storage.storage.get(new Fruit("banana"));
        Assert.assertEquals(actual, expected);
    }

    @Test (expected = RuntimeException.class)
    public void purchase_moreThanStorage_notOk() {
        TransactionDto transaction = new TransactionDto("p", "banana", 11);
        purchaseHandler.apply(transaction);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
