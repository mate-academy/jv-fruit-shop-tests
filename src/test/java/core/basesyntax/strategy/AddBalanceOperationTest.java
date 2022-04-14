package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddBalanceOperationTest {
    private static final FruitRecordDto fruitRecordDtoCorrect1 =
            new FruitRecordDto(Operation.BALANCE, "banana", 10);
    private static final FruitRecordDto fruitRecordDtoCorrect2 =
            new FruitRecordDto(Operation.BALANCE, "apple", 30);
    private static final FruitRecordDto fruitRecordDtoFruitNameEmpty =
            new FruitRecordDto(Operation.BALANCE, "", 100);
    private static final FruitRecordDto fruitRecordDtoFruitNameNumber =
            new FruitRecordDto(Operation.BALANCE, "1234", 100);
    private static final FruitRecordDto fruitRecordDtoWrongQuantity =
            new FruitRecordDto(Operation.BALANCE, "apple", -20);
    private static final FruitOperationHandler operationHandler = new AddBalanceOperation();
    private static Map<Fruit, Integer> expected;
    private static Map<Fruit, Integer> actual;

    @BeforeClass
    public static void beforeClass() {
        Storage.fruitsDataBase.clear();
    }

    @Test
    public void apply_addCorrectCase_Ok() {
        operationHandler.apply(fruitRecordDtoCorrect1);
        operationHandler.apply(fruitRecordDtoCorrect2);

        expected = Map.of(new Fruit("banana"), 10, new Fruit("apple"), 30);
        actual = Storage.fruitsDataBase;
        assertEquals(expected, actual);
    }

    @Test
    public void apply_addEquals_FruitRecordDto_Ok() {
        operationHandler.apply(fruitRecordDtoCorrect1);
        operationHandler.apply(fruitRecordDtoCorrect1);

        expected = Map.of(new Fruit("banana"), 10);
        actual = Storage.fruitsDataBase;
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void apply_addIncorrectFruitNameEmpty_Ok() {
        operationHandler.apply(fruitRecordDtoFruitNameEmpty);
    }

    @Test (expected = RuntimeException.class)
    public void apply_addIncorrectFruitNameWithNumber_Ok() {
        operationHandler.apply(fruitRecordDtoFruitNameNumber);
    }

    @Test (expected = RuntimeException.class)
    public void apply_addIncorrectQuantity_Ok() {
        operationHandler.apply(fruitRecordDtoWrongQuantity);
    }

    @After
    public void tearDown() {
        Storage.fruitsDataBase.entrySet().clear();
    }
}
