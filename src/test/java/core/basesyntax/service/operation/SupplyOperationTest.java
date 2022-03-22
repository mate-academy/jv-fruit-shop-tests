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

public class SupplyOperationTest {
    private static OperationHandler supplyOperationHandler;
    private static OperationHandler balanceOperationHandler;
    private Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        supplyOperationHandler = new SupplyOperationHandler();
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Before
    public void setUp() {
        FruitStorage.fruits.clear();
        fruit = new Fruit("apple", 14);
        balanceOperationHandler.doOperation(fruit);
    }

    @Test(expected = RuntimeException.class)
    public void supply_NullFruit_NotOk() {
        supplyOperationHandler.doOperation(null);
    }

    @Test(expected = NullPointerException.class)
    public void supply_FruitWithNullName_NotOk() {
        supplyOperationHandler.doOperation(new Fruit(null, 14));
    }

    @Test(expected = WrongNameException.class)
    public void supply_LineWithUnderscoredName_NotOk() {
        supplyOperationHandler.doOperation(new Fruit("apple_pie", 14));
    }

    @Test(expected = WrongNameException.class)
    public void supply_LineWithNameContainsNumber_NotOk() {
        supplyOperationHandler.doOperation(new Fruit("apple0", 14));
    }

    @Test(expected = WrongNameException.class)
    public void supply_LineWithFirstWrongSymbolInFruitName_NotOk() {
        supplyOperationHandler.doOperation(new Fruit("-apple", 14));
    }

    @Test(expected = WrongQuantityException.class)
    public void supply_LineWithNegativeQuantity_NotOk() {
        supplyOperationHandler.doOperation(new Fruit("apple", -7));
    }

    @Test(expected = WrongQuantityException.class)
    public void supply_LineWithZeroQuantity_NotOk() {
        supplyOperationHandler.doOperation(new Fruit("apple", 0));
    }

    @Test
    public void supply_BasicFruit_Ok() {
        supplyOperationHandler.doOperation(fruit);
        Fruit expectedFruit = new Fruit("apple", 28);
        Assert.assertEquals(expectedFruit, FruitStorage.fruits.get(0));
    }

    @Test(expected = NoSuchFruitException.class)
    public void supply_UnknownFruit_NotOk() {
        supplyOperationHandler.doOperation(new Fruit("pear", 9));
    }
}
