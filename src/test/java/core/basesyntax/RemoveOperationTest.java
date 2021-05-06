package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.Operation;
import core.basesyntax.service.impl.RemoveOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class RemoveOperationTest {
    private static Operation operation;
    private int actual;
    private int expected;
    private FruitRecordDto fruitRecordDto;

    @BeforeClass
    public static void initialization() {
        operation = new RemoveOperation();
    }

    @Test
    public void removeOperation_emptyStorageRemoveZero_OK() {
        fruitRecordDto = new FruitRecordDto(OperationType.PURCHASE, "apple", 0);
        expected = 0;
        actual = operation.apply(fruitRecordDto);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void applyOperation_emptyStorageSubtractPositiveNumber_notOk() {
        fruitRecordDto = new FruitRecordDto(OperationType.PURCHASE, "banana", 15);
        operation.apply(fruitRecordDto);
    }

    @Test(expected = RuntimeException.class)
    public void applyOperation_notEmptyStorageSubtractMoreThanBalance_notOk() {
        Storage.fruits.put(new Fruit("banana"), 60);
        fruitRecordDto = new FruitRecordDto(OperationType.PURCHASE, "banana", 80);
        operation.apply(fruitRecordDto);
    }

    @Test
    public void applyOperation_notEmptyStorageSubtractLessThanBalance_ok() {
        Storage.fruits.put(new Fruit("apple"), 40);
        fruitRecordDto = new FruitRecordDto(OperationType.PURCHASE, "apple", 20);
        expected = 20;
        actual = operation.apply(fruitRecordDto);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void applyOperation_fruitPutToStorageAfterApplying_ok() {
        Fruit apple = new Fruit("apple");
        Storage.fruits.put(apple, 200);
        fruitRecordDto = new FruitRecordDto(OperationType.PURCHASE, "apple", 100);
        operation.apply(fruitRecordDto);
        expected = 100;
        int quantityInStorage = Storage.fruits.get(apple);
        Assert.assertEquals(expected, quantityInStorage);
    }

    @After
    public void clearAfterEachTest() {
        Storage.fruits.clear();
    }
}
