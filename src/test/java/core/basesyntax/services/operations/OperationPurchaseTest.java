package core.basesyntax.services.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TypeOfOperation;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationPurchaseTest {
    private static FruitDto fruitDto;
    private static Fruit fruit;
    private static OperationHandler operationHandler;

    @Before
    public void setUp() {
        fruitDto = new FruitDto(TypeOfOperation.BALANCE, new Fruit("banana"), 20);
        fruit = new Fruit("banana");
        operationHandler = new OperationPurchase();

    }

    @Test(expected = NullPointerException.class)
    public void testingWithNullData_Ok() {
        operationHandler.apply(null);
    }

    @Test
    public void enoughFruitInStorage_Ok() {
        Storage.fruitStorage.put(fruit, 30);
        int expected = 10;
        int actual = operationHandler.apply(fruitDto);
        Assert.assertEquals(actual,expected);
    }

    @Test (expected = RuntimeException.class)
    public void balanceNotZero_Ok() {
        int actual = operationHandler.apply(fruitDto);
        int expected = 20;
        Assert.assertEquals(actual, expected);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }
}
