package core.basesyntax.strategy.handler;

import core.basesyntax.db.FruitDao;
import core.basesyntax.model.FruitTransaction;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private final FruitTransaction fruitTransaction = new FruitTransaction();
    private final BalanceOperationHandler balanceOperationHandler = new BalanceOperationHandler();

    @Test
    public void apply_correctBalance_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("mango");
        fruitTransaction.setQuantity(100);
        balanceOperationHandler.apply(fruitTransaction);
        int expected = 100;
        int actual = FruitDao.getQuantity("mango");
        Assert.assertEquals("Quantity must be equals", expected, actual);
    }

    @After
    public void tearDown() {
        FruitDao.storage.clear();
    }

}