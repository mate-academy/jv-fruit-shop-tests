package core.basesyntax.service.operation;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.exceptions.NoSuchFruitException;
import core.basesyntax.exceptions.WrongNameException;
import core.basesyntax.exceptions.WrongQuantityException;
import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationTest {
    private static OperationHandler returnOperationHandler;
    private static OperationHandler balanceOperationHandler;
    private Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        returnOperationHandler = new ReturnOperationHandler();
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Before
    public void setUp() {
        FruitStorage.fruits.clear();
        fruit = new Fruit("apple", 14);
        balanceOperationHandler.doOperation(fruit);
    }

    @Test(expected = RuntimeException.class)
    public void return_NullFruit_NotOk() {
        returnOperationHandler.doOperation(null);
    }

    @Test
    public void return_FruitWithWrongName_NotOk() {
        int expectedExceptionNumber = 4;
        int exceptionNumber = 0;
        try {
            returnOperationHandler.doOperation(new Fruit(null, 14));
        } catch (NullPointerException e) {
            exceptionNumber++;
            try {
                returnOperationHandler.doOperation(new Fruit("apple_pie", 14));
            } catch (WrongNameException ex) {
                exceptionNumber++;
                try {
                    returnOperationHandler.doOperation(new Fruit("apple0", 14));
                } catch (WrongNameException exc) {
                    exceptionNumber++;
                    try {
                        returnOperationHandler.doOperation(new Fruit("-apple", 14));
                    } catch (WrongNameException exception) {
                        exceptionNumber++;
                        try {
                            returnOperationHandler.doOperation(new Fruit("apple", 14));
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
    public void return_FruitWithWrongQuantity_NotOk() {
        int expectedExceptionNumber = 2;
        int exceptionNumber = 0;
        try {
            returnOperationHandler.doOperation(new Fruit("apple", -7));
        } catch (WrongQuantityException e) {
            exceptionNumber++;
            try {
                returnOperationHandler.doOperation(new Fruit("apple", 0));
            } catch (WrongQuantityException ex) {
                exceptionNumber++;
                try {
                    returnOperationHandler.doOperation(new Fruit("apple", 14));
                } catch (WrongQuantityException exception) {
                    exceptionNumber++;
                }
            }
        }
        Assert.assertEquals(expectedExceptionNumber, exceptionNumber);
    }

    @Test
    public void return_BasicFruit_Ok() {
        returnOperationHandler.doOperation(fruit);
        Fruit expectedFruit = new Fruit("apple", 28);
        Assert.assertEquals(expectedFruit, FruitStorage.fruits.get(0));
    }

    @Test(expected = NoSuchFruitException.class)
    public void return_UnknownFruit_NotOk() {
        returnOperationHandler.doOperation(new Fruit("pear", 9));
    }
}
