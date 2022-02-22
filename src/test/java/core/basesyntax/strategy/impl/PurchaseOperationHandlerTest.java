package core.basesyntax.strategy.impl;

import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final int OPERATION_COUNT = 2;
    private static OperationHandler operationHandler;
    private static FruitTransaction transactionOk;
    private static FruitTransaction transactionWithNegativeQuantity;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandler = new PurchaseOperationHandler();
        transactionOk = new FruitTransaction();
        transactionWithNegativeQuantity = new FruitTransaction();
    }

    @Before
    public void setUp() {
        transactionOk.setFruit("apple");
        transactionOk.setOperation(FruitTransaction.Operation.PURCHASE);
        transactionOk.setQuantity(40);
        transactionWithNegativeQuantity.setFruit("banana");
        transactionWithNegativeQuantity.setOperation(FruitTransaction.Operation.PURCHASE);
        transactionWithNegativeQuantity.setQuantity(-40);
    }

    @Test
    public void updateQuantityFruitInStorage_ok() {
        Fruit fruit = new Fruit("apple", 20);
        int quantityBeforeOperation = fruit.getQuantity();
        Storage.fruits.add(fruit);
        for (int i = 0; i < OPERATION_COUNT; i++) {
            operationHandler.updateQuantity(transactionOk);
        }
        int actual = Storage.fruits.get(0).getQuantity();
        int expected = quantityBeforeOperation - transactionOk.getQuantity() * OPERATION_COUNT;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateQuantityFruitIsNotInStorage_ok() {
        for (int i = 0; i < OPERATION_COUNT; i++) {
            operationHandler.updateQuantity(transactionOk);
        }
        int actual = Storage.fruits.get(0).getQuantity();
        int expected = -transactionOk.getQuantity() * OPERATION_COUNT;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void updateNegativeQuantity_notOk() {
        operationHandler.updateQuantity(transactionWithNegativeQuantity);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
