package core.basesyntax.service.operation;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.exceptions.RepeatedBalanceOperatorException;
import core.basesyntax.exceptions.WrongNameException;
import core.basesyntax.exceptions.WrongQuantityException;
import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationTest {
    private static OperationHandler balanceOperationHandler;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() throws Exception {
        balanceOperationHandler = new BalanceOperationHandler();
        fruit = new Fruit("apple", 14);
    }

    @Before
    public void setUp() {
        FruitStorage.fruits.clear();
    }

    @Test(expected = RuntimeException.class)
    public void balance_NullFruit_NotOk() {
        balanceOperationHandler.doOperation(null);
    }

    @Test(expected = NullPointerException.class)
    public void balance_FruitWithNullName_NotOk() {
        balanceOperationHandler.doOperation(new Fruit(null, 14));
    }

    @Test(expected = WrongNameException.class)
    public void balance_LineWithUnderscoredName_NotOk() {
        balanceOperationHandler.doOperation(new Fruit("apple_pie", 14));
    }

    @Test(expected = WrongNameException.class)
    public void balance_LineWithNameContainsNumber_NotOk() {
        balanceOperationHandler.doOperation(new Fruit("apple0", 14));
    }

    @Test(expected = WrongNameException.class)
    public void balance_LineWithFirstWrongSymbolInFruitName_NotOk() {
        balanceOperationHandler.doOperation(new Fruit("-apple", 14));
    }

    @Test(expected = WrongQuantityException.class)
    public void balance_LineWithNegativeQuantity_NotOk() {
        balanceOperationHandler.doOperation(new Fruit("apple", -7));
    }

    @Test(expected = WrongQuantityException.class)
    public void balance_LineWithZeroQuantity_NotOk() {
        balanceOperationHandler.doOperation(new Fruit("apple", 0));
    }

    @Test
    public void balance_BasicFruit_Ok() {
        balanceOperationHandler.doOperation(fruit);
        Fruit expectedFruit = new Fruit("apple", 14);
        Assert.assertEquals(expectedFruit, FruitStorage.fruits.get(0));
    }

    @Test(expected = RepeatedBalanceOperatorException.class)
    public void balance_RepeatedFruit_NotOk() {
        balanceOperationHandler.doOperation(fruit);
        balanceOperationHandler.doOperation(new Fruit("apple", 11));
    }
}
