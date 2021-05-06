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

public class AddOperationTest {
    private static OperationHandler addHandler;

    @BeforeClass
    public static void initHandler() {
        addHandler = new AddOperation();
    }

    @Before
    public void clearStorage() {
        Storage.fruits.clear();
    }

    @Test
    public void apply_addToStoreNotContainsFruit_Ok() {
        int expected = 20;
        int actual = addHandler.apply(
                new FruitRecordDto(OperationType.SUPPLY,
                        "banana",
                        20));
        assertEquals(expected, actual);
    }

    @Test
    public void apply_addToStoreAvailableFruit_Ok() {
        Storage.fruits.put(new Fruit("banana"), 20);
        int expected = 40;
        int actual = addHandler.apply(
                new FruitRecordDto(OperationType.SUPPLY,
                        "banana",
                        20));
        assertEquals(expected, actual);
    }
}
