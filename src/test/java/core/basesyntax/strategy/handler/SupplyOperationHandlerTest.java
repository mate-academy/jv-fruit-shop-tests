package core.basesyntax.strategy.handler;

import core.basesyntax.db.FruitDao;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static SupplyOperationHandler supplyOperationHandler;

    @BeforeClass
    public static void setUp() {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @Test
    public void apply_correctSupply_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        FruitDao.put("Avocado", 20);
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("Avocado");
        fruitTransaction.setQuantity(10);
        supplyOperationHandler.apply(fruitTransaction);
        int expected = 30;
        int actual = FruitDao
                .getQuantity("Avocado");
        Assert.assertEquals("There are "
                + expected
                + " avocado must be in storage", expected, actual);
    }

    @After
    public void tearDown() {
        FruitDao.storage.clear();
    }
}
