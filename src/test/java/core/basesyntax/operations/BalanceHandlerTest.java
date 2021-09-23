package core.basesyntax.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitOperationDto;
import core.basesyntax.model.OperationType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static OperationHandler operationHandler;
    private FruitOperationDto fruitOperationDto;
    private Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new BalanceHandler();
    }

    @Before
    public void setUp() {
        fruit = new Fruit("banana");
        fruitOperationDto = new FruitOperationDto(OperationType.BALANCE, fruit, 20);
    }

    @Test
    public void apply_BalanceWithValidData_Ok() {
        int expected = 20;
        int actual = operationHandler.apply(fruitOperationDto);
        assertEquals(expected, actual);
    }

    @Test
    public void apply_Rebalance_Ok() {
        FruitOperationDto newFruitOperationDto = new FruitOperationDto(
                OperationType.BALANCE, fruit, 150);
        int expected = 150;
        operationHandler.apply(fruitOperationDto);
        int actual = operationHandler.apply(newFruitOperationDto);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void apply_balanceWithNull_NotOk() {
        operationHandler.apply(null);
    }
}
