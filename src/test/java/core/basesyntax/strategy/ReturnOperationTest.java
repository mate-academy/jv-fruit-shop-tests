package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Storage;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationTest {
    private static final FruitRecordDto fruitRecordDtoCorrectInput =
            new FruitRecordDto(Operation.RETURN, "banana", 15);
    private static final FruitRecordDto fruitRecordDtoCorrectInput2 =
            new FruitRecordDto(Operation.RETURN, "citron", 0);
    private static final FruitRecordDto fruitRecordDtoUnknownFruit =
            new FruitRecordDto(Operation.RETURN, "check", 89);
    private static final FruitRecordDto fruitRecordDtoNegativeQuantity =
            new FruitRecordDto(Operation.RETURN, "apple", -30);
    private static final FruitOperationHandler operationHandler = new ReturnOperation();
    private static Map<Fruit, Integer> expected;
    private static Map<Fruit, Integer> actual;

    @Before
    public void setUp() {
        Storage.fruitsDataBase.put(new Fruit("banana"), 10);
        Storage.fruitsDataBase.put(new Fruit("citron"), 50);
        Storage.fruitsDataBase.put(new Fruit("apple"), 57);
    }

    @Test
    public void apply_returnFruits_Ok() {
        operationHandler.apply(fruitRecordDtoCorrectInput);
        operationHandler.apply(fruitRecordDtoCorrectInput2);

        actual = Storage.fruitsDataBase;
        expected = Map.of(new Fruit("banana"), 25,
                new Fruit("citron"), 50,
                new Fruit("apple"), 57);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_returnUnknownFruit_NotOk() {
        operationHandler.apply(fruitRecordDtoUnknownFruit);
    }

    @Test(expected = RuntimeException.class)
    public void apply_returnNegativeQuantity_NotOk() {
        operationHandler.apply(fruitRecordDtoNegativeQuantity);
    }

    @After
    public void clear() {
        Storage.fruitsDataBase.clear();
    }
}
