package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationTest {
    private static final FruitRecordDto fruitRecordDtoCorrect1 =
            new FruitRecordDto(Operation.RETURN,"banana", 10);
    private static final FruitRecordDto fruitRecordDtoCorrect2 =
            new FruitRecordDto(Operation.RETURN,"mango", 100);
    private static final FruitRecordDto fruitRecordDtoUnknownFruit =
            new FruitRecordDto(Operation.RETURN,"qqq", 100);
    private static final FruitRecordDto fruitRecordDtoNegativeQuantity =
            new FruitRecordDto(Operation.RETURN,"apple", -20);
    private static FruitOperationHandler operationHandler;
    private static Map<Fruit, Integer> expected;
    private static Map<Fruit, Integer> actual;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandler = new ReturnOperation();
    }

    @Before
    public void setUp() throws Exception {
        Storage.fruitsDataBase.put(new Fruit("banana"), 10);
        Storage.fruitsDataBase.put(new Fruit("mango"), 200);
        Storage.fruitsDataBase.put(new Fruit("apple"), 30);
    }

    @Test
    public void apply_returnFruits_Ok() {
        operationHandler.apply(fruitRecordDtoCorrect1);
        operationHandler.apply(fruitRecordDtoCorrect2);

        actual = Storage.fruitsDataBase;
        expected = Map.of(new Fruit("banana"), 20,
                new Fruit("mango"), 300,
                new Fruit("apple"), 30);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void apply_returnUnknownFruit_Ok() {
        operationHandler.apply(fruitRecordDtoUnknownFruit);
    }

    @Test (expected = RuntimeException.class)
    public void apply_returnNegativeQuantity_Ok() {
        operationHandler.apply(fruitRecordDtoNegativeQuantity);
    }
}
