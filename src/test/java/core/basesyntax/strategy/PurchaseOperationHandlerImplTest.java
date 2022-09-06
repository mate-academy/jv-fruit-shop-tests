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

public class PurchaseOperationHandlerImplTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseOperationHandlerImpl();
    }

    @Test(expected = RuntimeException.class)
    public void apply_littleFruitQuantity_ok() {
        Fruit fruit = new Fruit("banana");
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, fruit, 20);
        operationHandler.apply(transaction);
    }

    @Test
    public void apply_normalFruitQuantity_ok() {
        Fruit fruit = new Fruit("banana");
        Storage.dataBase.put(fruit, 107);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, new Fruit("banana"), 20);
        operationHandler.apply(transaction);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(fruit, 87);
        Assert.assertEquals("Incorrect output.", expected, Storage.dataBase);
    }

    @After
    public void tearDown() {
        Storage.dataBase.clear();
    }
}

