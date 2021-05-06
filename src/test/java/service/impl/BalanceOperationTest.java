package service.impl;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.OperationType;
import model.dto.FruitRecordDto;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import service.OperationHandler;

public class BalanceOperationTest {
    private static OperationHandler balanceHandler;

    @BeforeClass
    public static void initHandler() {
        balanceHandler = new BalanceOperation();
    }

    @Before
    public void clearStorage() {
        Storage.fruits.clear();
    }

    @Test
    public void apply_balanceToStoreNotContainsFruit_Ok() {
        int expected = 20;
        int actual = balanceHandler.apply(
                new FruitRecordDto(OperationType.BALANCE,
                        "banana",
                        20));
        assertEquals(expected, actual);
    }
}
