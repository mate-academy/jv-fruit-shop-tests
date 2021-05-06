package service.impl;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.Fruit;
import model.OperationType;
import model.dto.FruitRecordDto;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import service.OperationHandler;

public class RemoveOperationTest {
    private static OperationHandler removeHandler;

    @BeforeClass
    public static void initHandler() {
        removeHandler = new RemoveOperation();
    }

    @Before
    public void clearStorage() {
        Storage.fruits.clear();
    }

    @Test(expected = RuntimeException.class)
    public void apply_removeFromStoreNotContainsFruit_RuntimeException() {
        removeHandler.apply(new FruitRecordDto(OperationType.PURCHASE,
                        "banana",
                        20));
    }

    @Test(expected = RuntimeException.class)
    public void apply_removeFromStoreMoreThanAvailableFruit_RuntimeException() {
        Storage.fruits.put(new Fruit("banana"), 20);
        removeHandler.apply(new FruitRecordDto(OperationType.PURCHASE,
                        "banana",
                        40));
    }

    @Test
    public void apply_removeFromStoreAvailableFruit_Ok() {
        Storage.fruits.put(new Fruit("banana"), 20);
        int expected = 10;
        int actual = removeHandler.apply(
                new FruitRecordDto(OperationType.PURCHASE,
                        "banana",
                        10));
        assertEquals(expected, actual);
    }
}
