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

public class SupplyHandlerTest {
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;
    private static FruitTransaction supplyFruitTransaction;
    private static Exception exception;

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        operationHandler = new SupplyHandler(fruitDao);
        fruitTransaction = new FruitTransaction();
        supplyFruitTransaction = new FruitTransaction();
    }

    @Before
    public void setUp() {
        final String fruit = "apple";
        final int quantity = 10;
        final FruitTransaction.Operation operation = FruitTransaction.Operation.SUPPLY;
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(quantity);
        fruitTransaction.setOperation(operation);
        supplyFruitTransaction.setFruit("apple");
        supplyFruitTransaction.setQuantity(11);
        supplyFruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        exception = new Exception();
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
            exception = e;
        }
        Assert.assertSame(RuntimeException.class, exception.getClass());
    }

    @Test
    public void getHandler_nullFruitName_notOK() {
        fruitTransaction.setFruit(null);
        try {
            operationHandler.getHandler(fruitTransaction);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(RuntimeException.class, exception.getClass());
    }

    @Test
    public void getHandler_fruitNameWithNonLetters_notOK() {
        fruitTransaction.setFruit("apple1");
        try {
            operationHandler.getHandler(fruitTransaction);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(RuntimeException.class, exception.getClass());
    }

    @Test
    public void getHandler_nullOperation_notOK() {
        fruitTransaction.setOperation(null);
        try {
            operationHandler.getHandler(fruitTransaction);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(RuntimeException.class, exception.getClass());
    }

    @Test
    public void getHandler_negativeQuantity_notOK() {
        fruitTransaction.setQuantity(-1);
        try {
            operationHandler.getHandler(fruitTransaction);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(RuntimeException.class, exception.getClass());
    }

    @Test
    public void getHandler_nullFruitInDB_OK() {
        try {
            operationHandler.getHandler(fruitTransaction);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(Exception.class, exception.getClass());
        Assert.assertEquals(1, Storage.fruits.size());
        Assert.assertEquals("apple", Storage.fruits.get(fruitTransaction.getFruit()).getFruit());
        Assert.assertEquals(10,Storage.fruits.get(fruitTransaction.getFruit()).getQuantity());
        Assert.assertEquals(FruitTransaction.Operation.SUPPLY,
                Storage.fruits.get(fruitTransaction.getFruit()).getOperation());
    }

    @Test
    public void getHandler_notNullFruitInDB_OK() {
        Storage.fruits.put(fruitTransaction.getFruit(), fruitTransaction);
        supplyFruitTransaction.setQuantity(9);
        try {
            operationHandler.getHandler(supplyFruitTransaction);
        } catch (Exception e) {
            exception = e;
        }
        FruitTransaction.Operation operation;
        operation = Storage.fruits.get(fruitTransaction.getFruit()).getOperation();
        Assert.assertSame(Exception.class, exception.getClass());
        Assert.assertEquals(1, Storage.fruits.size());
        Assert.assertEquals("apple", Storage.fruits.get(fruitTransaction.getFruit()).getFruit());
        Assert.assertEquals(19,Storage.fruits.get(fruitTransaction.getFruit()).getQuantity());
        Assert.assertEquals(FruitTransaction.Operation.SUPPLY, operation);
    }
}
