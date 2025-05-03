package core.basesyntax.operation.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;
    private static FruitTransaction purchaseFruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        operationHandler = new PurchaseHandler(fruitDao);
        fruitTransaction = new FruitTransaction();
        purchaseFruitTransaction = new FruitTransaction();
    }

    @Before
    public void setUp() {
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(10);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseFruitTransaction.setFruit("apple");
        purchaseFruitTransaction.setQuantity(11);
        purchaseFruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void getHandler_nullFruitTransaction_notOK() {
        try {
            operationHandler.getHandler(null);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void getHandler_nullFruitName_notOK() {
        fruitTransaction.setFruit(null);
        try {
            operationHandler.getHandler(fruitTransaction);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void getHandler_fruitNameWithNonLetters_notOK() {
        fruitTransaction.setFruit("apple1");
        try {
            operationHandler.getHandler(fruitTransaction);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void getHandler_nullOperation_notOK() {
        fruitTransaction.setOperation(null);
        try {
            operationHandler.getHandler(fruitTransaction);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void getHandler_negativeQuantity_notOK() {
        fruitTransaction.setQuantity(-1);
        try {
            operationHandler.getHandler(fruitTransaction);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void getHandler_nullFruitInDB_notOK() {
        try {
            operationHandler.getHandler(fruitTransaction);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void getHandler_notEnoughFruitInDB_notOK() {
        Storage.fruits.put(fruitTransaction.getFruit(), fruitTransaction);
        try {
            operationHandler.getHandler(purchaseFruitTransaction);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void getHandler_goodData_OK() {
        Storage.fruits.put(fruitTransaction.getFruit(), fruitTransaction);
        purchaseFruitTransaction.setQuantity(9);
        operationHandler.getHandler(purchaseFruitTransaction);
        FruitTransaction.Operation operation =
                Storage.fruits.get(fruitTransaction.getFruit()).getOperation();
        Assert.assertEquals(1, Storage.fruits.size());
        Assert.assertEquals("apple", Storage.fruits.get(fruitTransaction.getFruit()).getFruit());
        Assert.assertEquals(1,Storage.fruits.get(fruitTransaction.getFruit()).getQuantity());
        Assert.assertEquals(FruitTransaction.Operation.PURCHASE, operation);
    }
}
