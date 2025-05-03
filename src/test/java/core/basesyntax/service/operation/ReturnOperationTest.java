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

    @Test(expected = NullPointerException.class)
    public void return_FruitWithNullName_NotOk() {
        returnOperationHandler.doOperation(new Fruit(null, 14));
    }

    @Test(expected = WrongNameException.class)
    public void return_LineWithUnderscoredName_NotOk() {
        returnOperationHandler.doOperation(new Fruit("apple_pie", 14));
    }

    @Test(expected = WrongNameException.class)
    public void return_LineWithNameContainsNumber_NotOk() {
        returnOperationHandler.doOperation(new Fruit("apple0", 14));
    }

    @Test(expected = WrongNameException.class)
    public void return_LineWithFirstWrongSymbolInFruitName_NotOk() {
        returnOperationHandler.doOperation(new Fruit("-apple", 14));
    }

    @Test(expected = WrongQuantityException.class)
    public void return_LineWithNegativeQuantity_NotOk() {
        returnOperationHandler.doOperation(new Fruit("apple", -7));
    }

    @Test(expected = WrongQuantityException.class)
    public void return_LineWithZeroQuantity_NotOk() {
        returnOperationHandler.doOperation(new Fruit("apple", 0));
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
