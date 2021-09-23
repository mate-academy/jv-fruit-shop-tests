package core.basesyntax.services.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TypeOfOperation;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationBalanceTest {
    private static FruitDto fruitDto;
    private OperationHandler operationHandler;

    @Before
    public void setUp() {
        operationHandler = new OperationBalance();
        Fruit fruit = new Fruit("banana");
        fruitDto = new FruitDto(TypeOfOperation.BALANCE, fruit, 20);
    }

    @Test
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
