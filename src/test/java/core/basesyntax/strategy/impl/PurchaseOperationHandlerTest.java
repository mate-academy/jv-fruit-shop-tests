package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static PurchaseOperationHandler operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        operationStrategy = new PurchaseOperationHandler();
        Storage.fruitStorage.clear();
        Storage.fruitStorage.put("banana", 10);
    }

    @Test(expected = RuntimeException.class)
    public void operationProcess_purchaseNonExistFruit_NotOk() {
        FruitTransaction testTransaction = new FruitTransaction("b", "kiwi", 10);
        operationStrategy.operationProcess(testTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void operationProcess_purchaseMoreThenHave_NotOk() {
        FruitTransaction testSupply = new FruitTransaction("s", "banana", 15);
        operationStrategy.operationProcess(testSupply);
    }

    @Test
    public void operationProcess_purchase_Ok() {
        FruitTransaction testTransaction = new FruitTransaction("s", "banana", 5);
        int oldValue = Storage.fruitStorage.get(testTransaction.getFruit());
        operationStrategy.operationProcess(testTransaction);
        assertEquals(Integer.valueOf(oldValue - testTransaction.getQuantity()),
                Storage.fruitStorage.get(testTransaction.getFruit()));
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }
}
