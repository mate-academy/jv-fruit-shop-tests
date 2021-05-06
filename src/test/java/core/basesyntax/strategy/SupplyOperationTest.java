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

public class SupplyOperationTest {
    private static final FruitRecordDto fruitRecordDtoCorrectInput =
            new FruitRecordDto(Operation.SUPPLY, "banana", 0);
    private static final FruitRecordDto fruitRecordDtoCorrectInput1 =
            new FruitRecordDto(Operation.SUPPLY, "citron", 100);
    private static final FruitRecordDto fruitRecordDtoNewFruit =
            new FruitRecordDto(Operation.SUPPLY, "apricot", 40);
    private static final FruitRecordDto fruitRecordDtoWrongNameFruit =
            new FruitRecordDto(Operation.SUPPLY, "123", 40);
    private static final FruitRecordDto fruitRecordDtoEmptyName =
            new FruitRecordDto(Operation.SUPPLY, "", 40);
    private static final FruitRecordDto fruitRecordDtoNegativeQuantity =
            new FruitRecordDto(Operation.SUPPLY, "apple", -30);
    private static final FruitOperationHandler operationHandler = new SupplyOperation();
    private static Map<Fruit, Integer> expected;
    private static Map<Fruit, Integer> actual;

    @Before
    public void setUp() {
        Storage.fruitsDataBase.put(new Fruit("banana"), 10);
        Storage.fruitsDataBase.put(new Fruit("citron"), 20);
        Storage.fruitsDataBase.put(new Fruit("apple"), 30);
    }

    @Test
    public void apply_supplyFruits_Ok() {
        operationHandler.apply(fruitRecordDtoCorrectInput);
        operationHandler.apply(fruitRecordDtoCorrectInput1);
        actual = Storage.fruitsDataBase;
        expected = Map.of(new Fruit("banana"), 10,
                new Fruit("citron"), 120,
                new Fruit("apple"), 30);
        assertEquals(expected, actual);
    }

    @Test
    public void apply_supplyNewFruit_Ok() {
        operationHandler.apply(fruitRecordDtoNewFruit);
        actual = Storage.fruitsDataBase;
        expected = Map.of(new Fruit("banana"), 10,
                new Fruit("citron"), 20,
                new Fruit("apple"), 30,
                new Fruit("apricot"), 40);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_supplyNegativeQuantity_Ok() {
        operationHandler.apply(fruitRecordDtoNegativeQuantity);
    }

    @After
    public void tearDown() {
        Storage.fruitsDataBase.clear();
    }
}
