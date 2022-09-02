package core.basesyntax.strategy;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class SupplyOperationHandlerImplTest {
    @Test
    public void apply_dataInDatabase_ok() {
        Fruit fruit = new Fruit("banana");
        Storage.dataBase.put(fruit, 113);
        OperationHandler operationHandler = new ReturnOperationHandlerImpl();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, new Fruit("banana"), 15);
        operationHandler.apply(transaction);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(fruit, 128);
        Assert.assertEquals("Incorrect output.", expected, Storage.dataBase);
    }

    @After
    public void tearDown() throws Exception {
        Storage.dataBase.clear();
    }
}
