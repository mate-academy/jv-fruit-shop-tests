package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balanceOperationHandler;
    private static FruitDao fruitDao;
    private static TransactionDto balanceTransaction;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        balanceOperationHandler = new BalanceOperationHandler(fruitDao);
        balanceTransaction = new TransactionDto("b", "orange", 10);
    }

    @Test
    public void apply_rightBalance_ok() {
        Fruit orange = new Fruit("orange");
        balanceOperationHandler.apply(balanceTransaction);
        Assert.assertNotNull(Storage.storage.get(orange));
        int expected = 10;
        Assert.assertEquals(java.util.Optional.of(expected).get(), Storage.storage.get(orange));
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
