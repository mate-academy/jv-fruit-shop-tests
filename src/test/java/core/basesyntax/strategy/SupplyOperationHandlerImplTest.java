package core.basesyntax.strategy;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerImplTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandler = new ReturnOperationHandlerImpl();
    }

    @Test
    public void apply_dataInDatabase_ok() {
        Fruit fruit = new Fruit("banana");
        Storage.dataBase.put(fruit, 113);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, new Fruit("banana"), 15);
        operationHandler.apply(transaction);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(fruit, 128);
        Assert.assertEquals("Incorrect output.", expected, Storage.dataBase);
    }

    @After
    public void tearDown() {
        Storage.dataBase.clear();
    }
}
