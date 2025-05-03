package core.basesyntax.services.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TypeOfOperation;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationSupplyTest {
    private static FruitDto fruitDto;
    private static Fruit fruit;
    private static OperationHandler operationHandler;

    @Before
    public void setUp() {
        fruitDto = new FruitDto(TypeOfOperation.BALANCE, new Fruit("apple"), 30);
        fruit = new Fruit("apple");
        operationHandler = new OperationSupply();
    }

    @Test(expected = NullPointerException.class)
    public void testingWithNullData_Ok() {
        operationHandler.apply(null);
    }

    @Test
    public void enoughFruitInStorage_Ok() {
        Storage.fruitStorage.put(fruit, 30);
        int expected = 60;
        int actual = operationHandler.apply(fruitDto);
        Assert.assertEquals(actual, expected);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }
}
