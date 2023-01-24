package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {

    @Before
    public void cleanBefore() {
        Storage.fruits.clear();
    }

    @After
    public void cleanAfter() {
        Storage.fruits.clear();
    }

    @Test
    public void supplyOperationHandler_process_Ok() {
        Storage.fruits.put("pineapple",200);
        Storage.fruits.put("strawberry",100);
        FruitTransaction f1 = new FruitTransaction(Operation.SUPPLY, "pineapple", 200);
        FruitTransaction f2 = new FruitTransaction(Operation.SUPPLY, "strawberry", 100);
        SupplyOperationHandler supplyOperationHandler = new SupplyOperationHandler();
        supplyOperationHandler.process(f1);
        supplyOperationHandler.process(f2);

        Assert.assertEquals(Storage.fruits.size(),2);
        Assert.assertEquals(Storage.fruits.get("pineapple"),(Integer) 400);
        Assert.assertEquals(Storage.fruits.get("strawberry"), (Integer) 200);
        Storage.fruits.clear();
    }
}
