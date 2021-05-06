package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

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

public class PurchaseOperationTest {
    private static FruitOperationHandler operation;
    private static final FruitRecordDto fruitRecordDtoWithWrongQuantity =
            new FruitRecordDto(Operation.BALANCE, "banana", 250);
    private static final FruitRecordDto fruitRecordDtoWithWrongFruitName =
            new FruitRecordDto(Operation.BALANCE, "lemon", 100);

    @BeforeClass
    public static void beforeClass() {
        operation = new PurchaseOperation();
    }

    @Before
    public void cleanMapDbBeforeStart() {
        Storage.fruits.clear();
    }

    @Before
    public void setUp() {
        Storage.fruits.put(new Fruit("banana"), 30);
        Storage.fruits.put(new Fruit("apple"), 70);
    }

    @Test
    public void apply_returnFruitQuantity_Ok() {
        operation.apply(new FruitRecordDto(Operation.PURCHASE, "banana", 15));
        operation.apply(new FruitRecordDto(Operation.PURCHASE, "apple", 40));

        Map<Fruit, Integer> actual = Storage.fruits;
        Map<Fruit, Integer> expected = Map.of(new Fruit("banana"), 15,
                new Fruit("apple"), 30);
        assertEquals(actual, expected);
    }

    @Test (expected = RuntimeException.class)
    public void apply_addIncorrectQuantity_NotOk() {
        operation.apply(fruitRecordDtoWithWrongQuantity);
    }

    @Test (expected = RuntimeException.class)
    public void apply_addIncorrectFruitName_NotOk() {
        operation.apply(fruitRecordDtoWithWrongFruitName);
    }

    @After
    public void cleanMapDB() {
        Storage.fruits.clear();
    }
}
