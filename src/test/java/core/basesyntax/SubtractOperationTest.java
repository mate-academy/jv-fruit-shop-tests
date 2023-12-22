package core.basesyntax;

import static core.basesyntax.service.impl.Operation.PURCHASE;

import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitOperationHandler;
import core.basesyntax.service.impl.SubtractOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubtractOperationTest {
    private static FruitOperationHandler fruitOperationHandler;
    private int actual;
    private int expected;
    private FruitRecordDto fruitRecordDto;

    @BeforeClass
    public static void initialization() {
        fruitOperationHandler = new SubtractOperation();
    }

    @Test
    public void applyOperation_emptyStorageSubtractZero_ok() {
        fruitRecordDto = new FruitRecordDto(PURCHASE, "apple", 0);
        expected = 0;
        actual = fruitOperationHandler.applyOperation(fruitRecordDto);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void applyOperation_emptyStorageSubtractPositiveNumber_notOk() {
        fruitRecordDto = new FruitRecordDto(PURCHASE, "banana", 15);
        fruitOperationHandler.applyOperation(fruitRecordDto);
    }

    @Test(expected = RuntimeException.class)
    public void applyOperation_notEmptyStorageSubtractMoreThanBalance_notOk() {
        Storage.fruits.put(new Fruit("banana"), 60);
        fruitRecordDto = new FruitRecordDto(PURCHASE, "banana", 80);
        fruitOperationHandler.applyOperation(fruitRecordDto);
    }

    @Test
    public void applyOperation_notEmptyStorageSubtractLessThanBalance_ok() {
        Storage.fruits.put(new Fruit("apple"), 40);
        fruitRecordDto = new FruitRecordDto(PURCHASE, "apple", 20);
        expected = 20;
        actual = fruitOperationHandler.applyOperation(fruitRecordDto);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void applyOperation_fruitPutToStorageAfterApplying_ok() {
        Fruit apple = new Fruit("apple");
        Storage.fruits.put(apple, 200);
        fruitRecordDto = new FruitRecordDto(PURCHASE, "apple", 100);
        fruitOperationHandler.applyOperation(fruitRecordDto);
        expected = 100;
        int quantityInStorage = Storage.fruits.get(apple);
        Assert.assertEquals(expected, quantityInStorage);
    }

    @After
    public void clearAfterEachTest() {
        Storage.fruits.clear();
    }
}
