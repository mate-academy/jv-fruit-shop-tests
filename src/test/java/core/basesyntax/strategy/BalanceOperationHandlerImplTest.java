package core.basesyntax.strategy;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class BalanceOperationHandlerImplTest {
    @Test
    public void apply_noDataInDatabase_ok() {
        Fruit fruit = new Fruit("banana");
        OperationHandler operationHandler = new BalanceOperationHandlerImpl();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, fruit, 20);
        operationHandler.apply(transaction);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(fruit, 20);
        Assert.assertEquals("Incorrect output.", expected, Storage.dataBase);
    }

    @Test
    public void apply_dataInDatabase_ok() {
        Fruit fruit = new Fruit("banana");
        Storage.dataBase.put(fruit, 107);
        OperationHandler operationHandler = new BalanceOperationHandlerImpl();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, fruit, 20);
        operationHandler.apply(transaction);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(fruit, 20);
        Assert.assertEquals("Incorrect output.", expected, Storage.dataBase);
    }

    @After
    public void tearDown() throws Exception {
        Storage.dataBase.clear();
    }
}
