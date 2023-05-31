package core.basesyntax.strategy.handler;

import core.basesyntax.db.FruitDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnOperationHandler;

    @BeforeClass
    public static void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
    }

    @Test
    public void apply_correctReturn_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        FruitDao.put("kiwi", 7);
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("kiwi");
        fruitTransaction.setQuantity(3);
        returnOperationHandler.apply(fruitTransaction);
        int expected = 10;
        int actual = FruitDao
                .getQuantity("kiwi");
        Assert.assertEquals("There are "
                + expected
                + " kiwi must be in storage ", expected, actual);
    }
}
