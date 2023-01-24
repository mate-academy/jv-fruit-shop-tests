package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {

    @Before
    public void clean() {
        Storage.fruits.clear();
    }

    @Test
    public void returnOperationHandler_process_Ok() {
        Storage.fruits.put("pineapple",200);
        Storage.fruits.put("strawberry",100);
        FruitTransaction f1 = new FruitTransaction(Operation.RETURN, "pineapple", 20);
        FruitTransaction f2 = new FruitTransaction(Operation.RETURN, "strawberry", 50);
        ReturnOperationHandler returnOperationHandler = new ReturnOperationHandler();
        returnOperationHandler.process(f1);
        returnOperationHandler.process(f2);

        Assert.assertEquals(Storage.fruits.size(),2);
        Assert.assertEquals(Storage.fruits.get("pineapple"),(Integer) 220);
        Assert.assertEquals(Storage.fruits.get("strawberry"), (Integer) 150);
        Storage.fruits.clear();
    }
}
