package core.basesyntax.strategy.impl;

import static junit.framework.TestCase.fail;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private FruitTransaction fruitTransaction;
    private BalanceOperationHandler balanceOperationHandler;

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction("b", "banana", 20);
        balanceOperationHandler = new BalanceOperationHandler(new FruitStorageDaoImpl());
    }

    @After
    public void tearDown() {
        Storage.fruitMap.clear();
    }

    @Test
    public void handle_addFruit_OK() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 20);
        balanceOperationHandler.handle(fruitTransaction);
        Assert.assertEquals(expected.containsKey("banana"), Storage.fruitMap.containsKey("banana"));
        Assert.assertEquals(expected.get("banana"), Storage.fruitMap.get("banana"));
    }

    @Test(expected = RuntimeException.class)
    public void handle_null_notOK() {
        balanceOperationHandler.handle(null);
    }

    @Test(expected = RuntimeException.class)
    public void handle_invalidOperation_notOK() {
        FruitTransaction transactionWithInvalidOperation
                = new FruitTransaction("t", "banana", 20);
        balanceOperationHandler.handle(transactionWithInvalidOperation);
        fail("We need inform user about unknown operation and throw RuntimeException");
    }
}
