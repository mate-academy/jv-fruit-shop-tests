package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Storage;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class BalanceOperationTest {
    private static final FruitRecordDto fruitRecordDtoCorrectInput =
            new FruitRecordDto(Operation.BALANCE, "banana", 15);
    private static final FruitRecordDto fruitRecordDtoCorrectInput1 =
            new FruitRecordDto(Operation.BALANCE, "apple", 35);
    private static final FruitOperationHandler operationHandler = new BalanceOperation();
    private static Map<Fruit, Integer> expected;
    private static Map<Fruit, Integer> actual;

    @Test
    public void apply_addCorrectCase_Ok() {
        operationHandler.apply(fruitRecordDtoCorrectInput);
        operationHandler.apply(fruitRecordDtoCorrectInput1);
        expected = Map.of(new Fruit("banana"), 15, new Fruit("apple"), 35);
        actual = Storage.fruitsDataBase;
        assertEquals(expected, actual);
    }

    @Test
    public void apply_addEquals_FruitRecordDto_Ok() {
        operationHandler.apply(fruitRecordDtoCorrectInput);
        operationHandler.apply(fruitRecordDtoCorrectInput);

        expected = Map.of(new Fruit("banana"), 15);
        actual = Storage.fruitsDataBase;
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruitsDataBase.entrySet().clear();
    }
}
