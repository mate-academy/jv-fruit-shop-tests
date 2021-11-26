package core.basesyntax.strategy;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static BalanceOperationHandler balanceHandler;
    private static FruitStorageDaoImpl fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitStorageDaoImpl();
        balanceHandler = new BalanceOperationHandler(fruitDao);
    }

    @Test
    public void balance_correctInput_ok() {
        int expected = 5;
        TransactionDto transaction = new TransactionDto("b", "banana", 5);
        balanceHandler.apply(transaction);
        int actual = Storage.storage.get(new Fruit("banana"));
        Assert.assertEquals(actual, expected);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
