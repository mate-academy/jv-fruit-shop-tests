package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationTest {
    private static final FruitRecordDto fruitRecordDtoCorrect1 =
            new FruitRecordDto(Operation.SUPPLY,"banana", 10);
    private static final FruitRecordDto fruitRecordDtoCorrect2 =
            new FruitRecordDto(Operation.SUPPLY,"mango", 100);
    private static final FruitRecordDto fruitRecordDtoNewFruit =
            new FruitRecordDto(Operation.SUPPLY,"pineapple", 40);
    private static final FruitRecordDto fruitRecordDtoWrongNameFruit =
            new FruitRecordDto(Operation.SUPPLY,"123", 40);
    private static final FruitRecordDto fruitRecordDtoEmptyName =
            new FruitRecordDto(Operation.SUPPLY,"", 40);
    private static final FruitRecordDto fruitRecordDtoNegativeQuantity =
            new FruitRecordDto(Operation.SUPPLY,"apple", -20);
    private static final FruitOperationHandler operationHandler = new SupplyOperation();
    private static Map<Fruit, Integer> expected;
    private static Map<Fruit, Integer> actual;

    @Before
    public void setUp() {
        Storage.fruitsDataBase.put(new Fruit("banana"), 10);
        Storage.fruitsDataBase.put(new Fruit("mango"), 20);
        Storage.fruitsDataBase.put(new Fruit("apple"), 30);
    }

    @Test
    public void apply_supplyFruits_Ok() {
        operationHandler.apply(fruitRecordDtoCorrect1);
        operationHandler.apply(fruitRecordDtoCorrect2);
        actual = Storage.fruitsDataBase;
        expected = Map.of(new Fruit("banana"), 20,
                new Fruit("mango"), 120,
                new Fruit("apple"), 30);
        assertEquals(expected, actual);
    }

    @Test
    public void apply_supplyNewFruit_Ok() {
        operationHandler.apply(fruitRecordDtoNewFruit);
        actual = Storage.fruitsDataBase;
        expected = Map.of(new Fruit("banana"), 10,
                new Fruit("mango"), 20,
                new Fruit("apple"), 30,
                new Fruit("pineapple"), 40);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void apply_supplyNegativeQuantity_Ok() {
        operationHandler.apply(fruitRecordDtoNegativeQuantity);
    }

    @Test (expected = RuntimeException.class)
    public void apply_supplyWrongNameFruit_Ok() {
        operationHandler.apply(fruitRecordDtoWrongNameFruit);
    }

    @Test (expected = RuntimeException.class)
    public void apply_supplyEmptyNameFruit_Ok() {
        operationHandler.apply(fruitRecordDtoEmptyName);
    }

    @After
    public void tearDown() {
        Storage.fruitsDataBase.clear();
    }
}
