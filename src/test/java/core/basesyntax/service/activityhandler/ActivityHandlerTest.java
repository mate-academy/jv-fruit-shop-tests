package core.basesyntax.service.activityhandler;

import core.basesyntax.exceptions.NotEnoughFruitsInStorageException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
        storage.put("apple", 10);
        storage.put("pear", 15);
    }

    @Test
    void balanceHandlerProcessActivity_Ok() {
        activityHandler = new BalanceHandler();
        try {
            activityHandler.processActivity(storage, firstFruit, firstAmount);
        } catch (NotEnoughFruitsInStorageException e) {
            Assertions.fail(noExceptionMessage);
        }
        Assertions.assertEquals(firstAmount, storage.get(firstFruit));
    }

    @Test
    void purcahseHandlerProcessActivity_Ok() {
        activityHandler = new PurchaseHandler();
        try {
            activityHandler.processActivity(storage, secondFruit, firstAmount);
        } catch (NotEnoughFruitsInStorageException e) {
            Assertions.fail(noExceptionMessage);
        }
        Assertions.assertEquals(zero, storage.get(secondFruit));
    }

    @Test
    void purcahseHandlerProcessActivity_NotOk() {
        activityHandler = new PurchaseHandler();
        try {
            activityHandler.processActivity(storage, secondFruit, secondAmount);
        } catch (NotEnoughFruitsInStorageException e) {
            Assertions.assertEquals(notEnoughFruitsMessage, e.getMessage());
            return;
        }
        Assertions.fail(exceptionMessage);

    }

    @Test
    void returnHandlerProcessActivity_Ok() {
        activityHandler = new ReturnHandler();
        try {
            activityHandler.processActivity(storage, secondFruit, firstAmount);
        } catch (NotEnoughFruitsInStorageException e) {
            Assertions.fail(noExceptionMessage);
        }
        Assertions.assertEquals(Integer.valueOf(20), storage.get(secondFruit));
    }

    @Test
    void supplyHandlerProcessActivity_Ok() {
        activityHandler = new SupplyHandler();
        try {
            activityHandler.processActivity(storage, secondFruit, secondAmount);
        } catch (NotEnoughFruitsInStorageException e) {
            Assertions.fail(noExceptionMessage);
        }
        Assertions.assertEquals(Integer.valueOf(25), storage.get(secondFruit));
    }
}
