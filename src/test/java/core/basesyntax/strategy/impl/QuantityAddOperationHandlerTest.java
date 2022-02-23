package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class QuantityAddOperationHandlerTest {
    private static QuantityAddOperationHandler operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        operationStrategy = new QuantityAddOperationHandler();
        Storage.fruitStorage.clear();
        Storage.fruitStorage.put("banana", 10);
    }

    @Test
    public void operationProcess_addNewFruit_Ok() {
        FruitTransaction testBalance = new FruitTransaction("b", "kiwi", 10);
        int oldSize = Storage.fruitStorage.size();
        operationStrategy.operationProcess(testBalance);
        assertEquals(oldSize + 1, Storage.fruitStorage.size());
        assertEquals(Integer.valueOf(testBalance.getQuantity()),
                Storage.fruitStorage.get(testBalance.getFruit()));
    }

    @Test
    public void operationProcess_addExistFruit_Ok() {
        FruitTransaction testSupply = new FruitTransaction("s", "banana", 15);
        int oldValue = Storage.fruitStorage.get(testSupply.getFruit());
        operationStrategy.operationProcess(testSupply);
        assertEquals(Integer.valueOf(oldValue + testSupply.getQuantity()),
                Storage.fruitStorage.get(testSupply.getFruit()));
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }
}
