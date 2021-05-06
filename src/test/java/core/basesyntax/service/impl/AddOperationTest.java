package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitOperationHandler;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationTest {
    private static FruitOperationHandler operation;
    private static final FruitRecordDto fruitRecordDtoWithWrongQuantity =
            new FruitRecordDto(Operation.BALANCE, "banana", -50);

    @BeforeClass
    public static void beforeClass() {
        operation = new AddOperation();
    }

    @Before
    public void setUp() {
        Storage.fruits.put(new Fruit("banana"), 30);
        Storage.fruits.put(new Fruit("apple"), 70);
    }

    @Test
    public void apply_addFruitWithNullValue_NotOk() {
        Fruit fruit = new Fruit(null);
        Storage.fruits.put(fruit,null);
        assertNull(Storage.fruits.get(fruit));
    }

    @Test
    public void apply_balanceAmountTest_Ok() {
        int actual = operation.apply(new FruitRecordDto(Operation.BALANCE, "banana", 25));
        assertEquals(25, actual);
    }

    @Test
    public void apply_returnFruitQuantity_Ok() {
        operation.apply(new FruitRecordDto(Operation.RETURN, "banana", 15));
        Map<Fruit, Integer> actual = Storage.fruits;
        Map<Fruit, Integer> expected = Map.of(new Fruit("banana"), 45,
                new Fruit("apple"), 70);
        assertEquals(actual, expected);
    }

    @Test
    public void apply_supplyFruitQuantity_Ok() {
        operation.apply(new FruitRecordDto(Operation.SUPPLY, "lemon", 15));
        Map<Fruit, Integer> actual = Storage.fruits;
        Map<Fruit, Integer> expected = Map.of(new Fruit("banana"), 30,
                new Fruit("apple"), 70, new Fruit("lemon"), 15);
        assertEquals(actual, expected);
    }

    @Test (expected = RuntimeException.class)
    public void apply_addIncorrectQuantity_NotOk() {
        operation.apply(fruitRecordDtoWithWrongQuantity);
    }

    @After
    public void cleanMapDB() {
        Storage.fruits.clear();
    }
}
