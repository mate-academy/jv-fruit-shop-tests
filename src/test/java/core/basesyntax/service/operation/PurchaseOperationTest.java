package core.basesyntax.service.operation;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.exceptions.NoSuchFruitException;
import core.basesyntax.exceptions.OverPurchaseException;
import core.basesyntax.exceptions.WrongNameException;
import core.basesyntax.exceptions.WrongQuantityException;
import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationTest {
    private static OperationHandler purchaseOperationHandler;
    private static OperationHandler balanceOperationHandler;
    private Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Before
    public void setUp() {
        FruitStorage.fruits.clear();
        fruit = new Fruit("apple", 15);
        balanceOperationHandler.doOperation(fruit);
    }

    @Test(expected = RuntimeException.class)
    public void purchase_NullFruit_NotOk() {
        purchaseOperationHandler.doOperation(null);
    }

    @Test
    public void purchase_FruitWithWrongName_NotOk() {
        int expectedExceptionNumber = 4;
        int exceptionNumber = 0;
        try {
            purchaseOperationHandler.doOperation(new Fruit(null, 14));
        } catch (NullPointerException e) {
            exceptionNumber++;
            try {
                purchaseOperationHandler.doOperation(new Fruit("apple_pie", 14));
            } catch (WrongNameException ex) {
                exceptionNumber++;
                try {
                    purchaseOperationHandler.doOperation(new Fruit("apple0", 14));
                } catch (WrongNameException exc) {
                    exceptionNumber++;
                    try {
                        purchaseOperationHandler.doOperation(new Fruit("-apple", 14));
                    } catch (WrongNameException exception) {
                        exceptionNumber++;
                        try {
                            purchaseOperationHandler.doOperation(new Fruit("apple", 14));
                        } catch (WrongNameException notException) {
                            exceptionNumber++;
                        }
                    }
                }
            }
        }
        Assert.assertEquals(expectedExceptionNumber, exceptionNumber);
    }

    @Test
    public void purchase_FruitWithWrongQuantity_NotOk() {
        int expectedExceptionNumber = 2;
        int exceptionNumber = 0;
        try {
            purchaseOperationHandler.doOperation(new Fruit("apple", -7));
        } catch (WrongQuantityException e) {
            exceptionNumber++;
            try {
                purchaseOperationHandler.doOperation(new Fruit("apple", 0));
            } catch (WrongQuantityException ex) {
                exceptionNumber++;
                try {
                    purchaseOperationHandler.doOperation(new Fruit("apple", 14));
                } catch (WrongQuantityException exception) {
                    exceptionNumber++;
                }
            }
        }
        Assert.assertEquals(expectedExceptionNumber, exceptionNumber);
    }

    @Test
    public void purchase_BasicFruit_Ok() {
        purchaseOperationHandler.doOperation(fruit);
        Fruit expectedFruit = new Fruit("apple", 28);
        Assert.assertEquals(expectedFruit, FruitStorage.fruits.get(0));
    }

    @Test(expected = NoSuchFruitException.class)
    public void purchase_UnknownFruit_NotOk() {
        purchaseOperationHandler.doOperation(new Fruit("pear", 9));
    }

    @Test(expected = OverPurchaseException.class)
    public void purchase_Overbuy_NotOk() {
        purchaseOperationHandler.doOperation(new Fruit("apple", 20));
    }
}
