package core.basesyntax.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitOperationDto;
import core.basesyntax.model.OperationType;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static OperationHandler operationHandler;
    private static FruitOperationDto fruitOperationDto;
    private static Fruit fruit;
    private int expected;
    private int actual;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new BalanceHandler();
        fruit = new Fruit("banana");
        fruitOperationDto = new FruitOperationDto(OperationType.BALANCE, fruit, 20);
    }

    @Test
    public void apply_BalanceWithValidData_Ok() {
        expected = 20;
        actual = operationHandler.apply(fruitOperationDto);
        assertEquals(expected, actual);
    }

    @Test
    public void apply_Rebalance_Ok() {
        FruitOperationDto newFruitOperationDto = new FruitOperationDto(
                OperationType.BALANCE, fruit, 150);
        expected = 150;
        actual = operationHandler.apply(newFruitOperationDto);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_balanceWithNull_NotOk() {
        operationHandler.apply(null);
    }
}
