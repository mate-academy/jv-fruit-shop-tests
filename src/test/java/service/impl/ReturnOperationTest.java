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

public class ReturnOperationTest {
    private static OperationHandler returnHandler;

    @BeforeClass
    public static void initHandler() {
        returnHandler = new ReturnOperation();
    }

    @Before
    public void clearStorage() {
        Storage.fruits.clear();
    }

    @Test
    public void apply_returnToStoreNotContainsFruit_Ok() {
        int expected = 20;
        int actual = returnHandler.apply(
                new FruitRecordDto(OperationType.RETURN,"banana",20));
        assertEquals(expected, actual);
    }

    @Test
    public void apply_returnToStoreAvailableFruit_Ok() {
        Storage.fruits.put(new Fruit("banana"), 20);
        int expected = 40;
        int actual = returnHandler.apply(
                new FruitRecordDto(OperationType.RETURN,"banana",20));
        assertEquals(expected, actual);
    }
}
