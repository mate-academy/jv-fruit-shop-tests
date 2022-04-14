package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Storage;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationTest {
    private static final FruitRecordDto BANANA =
            new FruitRecordDto(Operation.BALANCE, "banana", 15);
    private static final FruitRecordDto APPLE =
            new FruitRecordDto(Operation.BALANCE, "apple", 35);
    private static FruitOperationHandler operationHandler;
    private static Map<Fruit, Integer> expected;
    private static Map<Fruit, Integer> actual;

    @BeforeClass
    public static void initializeObject() {
        operationHandler = new BalanceOperation();
    }

    @Test
    public void apply_addCorrectCase_Ok() {
        operationHandler.apply(BANANA);
        operationHandler.apply(APPLE);
        expected = Map.of(new Fruit("banana"), 15, new Fruit("apple"), 35);
        actual = Storage.fruitsDataBase;
        assertEquals(expected, actual);
    }

    @Test
    public void apply_addEqualsFruitRecordDto_Ok() {
        operationHandler.apply(BANANA);
        operationHandler.apply(BANANA);

        expected = Map.of(new Fruit("banana"), 15);
        actual = Storage.fruitsDataBase;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_negativeAmount_NotOk() {
        operationHandler.apply(
                new FruitRecordDto(Operation.BALANCE, "banana", -15));
    }

    @After
    public void clear() {
        Storage.fruitsDataBase.entrySet().clear();
    }
}
