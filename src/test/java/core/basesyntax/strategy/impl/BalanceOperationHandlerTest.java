package core.basesyntax.strategy.impl;

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

    @Test
    public void handle_nullQuantity_OK() {
        Integer expected = 0;
        FruitTransaction fruitTransactionWithNullQuantity =
                new FruitTransaction("b", "banana", null);
        balanceOperationHandler.handle(fruitTransactionWithNullQuantity);
        Assert.assertEquals(expected, Storage.fruitMap.get("banana"));
    }
}
