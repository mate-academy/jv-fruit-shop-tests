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

public class SupplyOperationHandlerTest {
    private FruitTransaction fruitTransaction;
    private SupplyOperationHandler supplyOperationHandler;

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction("s", "banana", 20);
        supplyOperationHandler = new SupplyOperationHandler(new FruitStorageDaoImpl());
    }

    @After
    public void tearDown() {
        Storage.fruitMap.clear();
    }

    @Test
    public void handle_supplyFruit_OK() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 20);
        supplyOperationHandler.handle(fruitTransaction);
        Assert.assertEquals(expected.containsKey("banana"), Storage.fruitMap.containsKey("banana"));
        Assert.assertEquals(expected.get("banana"), Storage.fruitMap.get("banana"));
    }
}
