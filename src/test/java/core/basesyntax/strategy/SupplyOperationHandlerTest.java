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

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyHandler;
    private static FruitStorageDaoImpl fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitStorageDaoImpl();
        supplyHandler = new SupplyOperationHandler(fruitDao);
    }

    @Before
    public void setUp() {
        fruitDao.add(new Fruit("orange"), 5);
    }

    @Test
    public void supply_correctInput_ok() {
        int expected = 1000;
        TransactionDto transaction = new TransactionDto("s", "orange", 995);
        supplyHandler.apply(transaction);
        int actual = Storage.storage.get(new Fruit("orange"));
        Assert.assertEquals(actual, expected);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
