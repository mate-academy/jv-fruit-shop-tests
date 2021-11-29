package core.basesyntax.service.activityhandler;

import core.basesyntax.exceptions.NotEnoughFruitsInStorageException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ActivityHandlerTest {
    private static Map<String, Integer> storage;
    private static ActivityHandler activityHandler;
    private static final String notEnoughFruitsMessage = "Not enough fruits in storage";
    private static final String noExceptionMessage = "Not expected exception";
    private static final String exceptionMessage = "Expected exception";
    private static final String firstFruit = "banana";
    private static final String secondFruit = "apple";
    private static final Integer firstAmount = 10;
    private static final Integer secondAmount = 15;
    private static final Integer zero = 0;

    @Before
    public void setUp() {
        storage = new HashMap<>();
        storage.put("apple", 10);
        storage.put("pear", 15);
    }

    @Test
    public void balanceHandlerProcessActivity_Ok() {
        activityHandler = new BalanceHandler();
        try {
            activityHandler.processActivity(storage, firstFruit, firstAmount);
        } catch (NotEnoughFruitsInStorageException e) {
            Assert.fail(noExceptionMessage);
        }
        Assert.assertEquals(firstAmount, storage.get(firstFruit));
    }

    @Test
    public void purcahseHandlerProcessActivity_Ok() {
        activityHandler = new PurchaseHandler();
        try {
            activityHandler.processActivity(storage, secondFruit, firstAmount);
        } catch (NotEnoughFruitsInStorageException e) {
            Assert.fail(noExceptionMessage);
        }
        Assert.assertEquals(zero, storage.get(secondFruit));
    }

    @Test
    public void purcahseHandlerProcessActivity_NotOk() {
        activityHandler = new PurchaseHandler();
        try {
            activityHandler.processActivity(storage, secondFruit, secondAmount);
        } catch (NotEnoughFruitsInStorageException e) {
            Assert.assertEquals(notEnoughFruitsMessage, e.getMessage());
            return;
        }
        Assert.fail(exceptionMessage);

    }

    @Test
    public void returnHandlerProcessActivity_Ok() {
        activityHandler = new ReturnHandler();
        try {
            activityHandler.processActivity(storage, secondFruit, firstAmount);
        } catch (NotEnoughFruitsInStorageException e) {
            Assert.fail(noExceptionMessage);
        }
        Assert.assertEquals(Integer.valueOf(20), storage.get(secondFruit));
    }

    @Test
    public void supplyHandlerProcessActivity_Ok() {
        activityHandler = new SupplyHandler();
        try {
            activityHandler.processActivity(storage, secondFruit, secondAmount);
        } catch (NotEnoughFruitsInStorageException e) {
            Assert.fail(noExceptionMessage);
        }
        Assert.assertEquals(Integer.valueOf(25), storage.get(secondFruit));
    }
}
