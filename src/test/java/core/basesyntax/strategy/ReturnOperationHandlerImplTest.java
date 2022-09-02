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

public class ReturnOperationHandlerImplTest {
    private Map<Fruit, Integer> dataBase = Storage.dataBase;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Storage.dataBase.clear();
    }

    @Test
    public void apply_dataInDatabase_ok() {
        Fruit fruit = new Fruit("banana");
        dataBase.put(fruit, 107);
        OperationHandler operationHandler = new ReturnOperationHandlerImpl();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, new Fruit("banana"), 20);
        operationHandler.apply(transaction);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(fruit, 127);
        Assert.assertEquals("Incorrect output.", expected, dataBase);
    }

    @After
    public void tearDown() throws Exception {
        dataBase.clear();
    }
}
