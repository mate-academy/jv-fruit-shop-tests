package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.impl.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class OperationHandlerTest {
    //Balance Handle Tests
    @Test
    public void get_balanceOperationHandler_Ok() {
        FruitTransaction fruitTransaction1 = new FruitTransaction(OperationType.BALANCE,
                "banana", 40);
        FruitTransaction fruitTransaction2 = new FruitTransaction(OperationType.BALANCE,
                "apple", 80);
        FruitTransaction fruitTransaction3 = new FruitTransaction(OperationType.BALANCE,
                "orange", 30);

        OperationHandler balanceOperationHandler = new BalanceOperationHandlerImpl();
        balanceOperationHandler.handle(fruitTransaction1);
        balanceOperationHandler.handle(fruitTransaction2);
        balanceOperationHandler.handle(fruitTransaction3);

        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("banana", 40);
        expectedStorage.put("apple", 80);
        expectedStorage.put("orange", 30);

        Assert.assertEquals("Expected data: "
                + expectedStorage + " but was: "
                + FruitsStorage.fruitsStorage,
                expectedStorage, FruitsStorage.fruitsStorage);
    }

    @Test
    public void get_balanceOperationHandlerWithNull_NotOk() {
        OperationHandler balanceOperationHandler = new BalanceOperationHandlerImpl();
        assertThrows(ValidationException.class,
                () -> balanceOperationHandler.handle(null));
    }

    //Supply Handle Tests
    @Test
    public void get_supplyOperationHandler_Ok() {
        FruitsStorage.fruitsStorage.put("banana", 40);
        FruitsStorage.fruitsStorage.put("apple", 80);
        FruitsStorage.fruitsStorage.put("orange", 30);

        FruitTransaction fruitTransaction4 = new FruitTransaction(OperationType.SUPPLY,
                "banana", 10);
        FruitTransaction fruitTransaction5 = new FruitTransaction(OperationType.SUPPLY,
                "apple", 15);
        FruitTransaction fruitTransaction6 = new FruitTransaction(OperationType.SUPPLY,
                "orange", 20);

        OperationHandler supplyOperationHandler = new SupplyOperationHandlerImpl();
        supplyOperationHandler.handle(fruitTransaction4);
        supplyOperationHandler.handle(fruitTransaction5);
        supplyOperationHandler.handle(fruitTransaction6);

        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("banana", 50);
        expectedStorage.put("apple", 95);
        expectedStorage.put("orange", 50);

        Assert.assertEquals("Expected data: "
                        + expectedStorage + " but was: "
                        + FruitsStorage.fruitsStorage,
                expectedStorage, FruitsStorage.fruitsStorage);
    }

    @Test
    public void get_supplyOperationHandlerWithNull_NotOk() {
        OperationHandler supplyOperationHandler = new SupplyOperationHandlerImpl();
        assertThrows(ValidationException.class,
                () -> supplyOperationHandler.handle(null));
    }

    //Purchase Handle Tests
    @Test
    public void get_purchaseOperationHandler_Ok() {
        FruitsStorage.fruitsStorage.put("banana", 40);
        FruitsStorage.fruitsStorage.put("apple", 80);
        FruitsStorage.fruitsStorage.put("orange", 30);

        FruitTransaction fruitTransaction7 = new FruitTransaction(OperationType.PURCHASE,
                "banana", 30);
        FruitTransaction fruitTransaction8 = new FruitTransaction(OperationType.PURCHASE,
                "apple", 45);
        FruitTransaction fruitTransaction9 = new FruitTransaction(OperationType.PURCHASE,
                "orange", 20);

        OperationHandler purchaseOperationHandler = new PurchaseOperationHandlerImpl();
        purchaseOperationHandler.handle(fruitTransaction7);
        purchaseOperationHandler.handle(fruitTransaction8);
        purchaseOperationHandler.handle(fruitTransaction9);

        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("banana", 10);
        expectedStorage.put("apple", 35);
        expectedStorage.put("orange", 10);

        Assert.assertEquals("Expected data: "
                        + expectedStorage + " but was: "
                        + FruitsStorage.fruitsStorage,
                expectedStorage, FruitsStorage.fruitsStorage);
    }

    @Test
    public void get_purchaseOperationHandlerWithNull_NotOk() {
        OperationHandler purchaseOperationHandler = new PurchaseOperationHandlerImpl();
        assertThrows(ValidationException.class,
                () -> purchaseOperationHandler.handle(null));
    }

    //Return Handle Tests
    @Test
    public void get_returnOperationHandler_Ok() {
        FruitsStorage.fruitsStorage.put("banana", 40);
        FruitsStorage.fruitsStorage.put("apple", 80);
        FruitsStorage.fruitsStorage.put("orange", 30);

        FruitTransaction fruitTransaction10 = new FruitTransaction(OperationType.RETURN,
                "banana", 5);
        FruitTransaction fruitTransaction11 = new FruitTransaction(OperationType.RETURN,
                "apple", 10);
        FruitTransaction fruitTransaction12 = new FruitTransaction(OperationType.RETURN,
                "orange", 15);

        OperationHandler returnOperationHandler = new ReturnOperationHandlerImpl();
        returnOperationHandler.handle(fruitTransaction10);
        returnOperationHandler.handle(fruitTransaction11);
        returnOperationHandler.handle(fruitTransaction12);

        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("banana", 45);
        expectedStorage.put("apple", 90);
        expectedStorage.put("orange", 45);

        Assert.assertEquals("Expected data: "
                        + expectedStorage + " but was: "
                        + FruitsStorage.fruitsStorage,
                expectedStorage, FruitsStorage.fruitsStorage);
    }

    @Test
    public void get_returnOperationHandlerWithNull_NotOk() {
        OperationHandler returnOperationHandler = new ReturnOperationHandlerImpl();
        assertThrows(ValidationException.class,
                () -> returnOperationHandler.handle(null));
    }

    @AfterClass
    public static void afterClass() {
        FruitsStorage.fruitsStorage.clear();
    }
}
