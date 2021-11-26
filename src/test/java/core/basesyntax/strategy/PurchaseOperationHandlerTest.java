package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.OperationHandlerException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseOperationHandler;
    private static FruitDao fruitDao;
    private static TransactionDto purchaseTransaction;
    private static TransactionDto wrongPurchaseTransaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitDao = new FruitDaoImpl();
        purchaseOperationHandler = new PurchaseOperationHandler(fruitDao);
        purchaseTransaction = new TransactionDto("p", "orange", 5);
        wrongPurchaseTransaction = new TransactionDto("p", "orange", 30);

    }

    @Before
    public void beforeEachTest() {
        Fruit orange = new Fruit("orange");
        Storage.storage.put(orange, 20);
    }

    @Test
    public void apply_purchaseOperation_ok() {
        int expected = 15;
        Fruit orange = new Fruit("orange");
        purchaseOperationHandler.apply(purchaseTransaction);
        int actual = Storage.storage.get(orange);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = OperationHandlerException.class)
    public void apply_purchaseOperationNotEnoughQuantity_notOk() {
        purchaseOperationHandler.apply(wrongPurchaseTransaction);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
