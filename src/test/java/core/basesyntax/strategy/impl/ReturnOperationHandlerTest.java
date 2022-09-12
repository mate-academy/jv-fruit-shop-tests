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

public class ReturnOperationHandlerTest {
    private FruitTransaction fruitTransaction;
    private ReturnOperationHandler returnOperationHandler;

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction("r", "banana", 20);
        returnOperationHandler = new ReturnOperationHandler(new FruitStorageDaoImpl());
    }

    @After
    public void tearDown() {
        Storage.fruitMap.clear();
    }

    @Test
    public void handle_returnFruit_OK() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 20);
        returnOperationHandler.handle(fruitTransaction);
        Assert.assertEquals(expected.containsKey("banana"), Storage.fruitMap.containsKey("banana"));
        Assert.assertEquals(expected.get("banana"), Storage.fruitMap.get("banana"));
    }
}
