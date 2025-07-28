package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private OperationHandler operationHandler = new PurchaseOperation();

    @Test
    void purchase_FruitContainsNull_NotOk() {
        String fruit = null;
        int amount = 200;
        assertThrows(RuntimeException.class, () ->
                operationHandler.updateNumberOffFruit(fruit, amount));
    }

    @Test
    void purchase_BalanceIsNull_NotOk() {
        String fruit = "banana";
        int amount = 0;
        assertThrows(RuntimeException.class, () ->
                operationHandler.updateNumberOffFruit(fruit, amount));
    }

    @Test
    void purchase_EmptyFruit_NotOk() {
        String fruit = "";
        int amount = 40;
        assertThrows(RuntimeException.class, () ->
                operationHandler.updateNumberOffFruit(fruit, amount));
    }

    @Test
    void purchase_CheckForNoExceptions_Ok() {
        String fruit = "apple";
        int amount = 20;
        String expected = "0";
        Storage.storage.put(fruit, amount);
        operationHandler.updateNumberOffFruit(fruit, amount);
        assertEquals(expected, String.valueOf(Storage.storage.get(fruit)));
    }

    @Test
    void purchase_NotEnoughQuality_NotOk() {
        String fruit = "apple";
        int amount = 200;
        assertThrows(RuntimeException.class, () ->
                operationHandler.updateNumberOffFruit(fruit, amount));
    }

    @Test
    void purchase_FruitWithSpeciallySymbols_NotOk() {
        String fruit = "$%%#^#^@#^";
        int amount = 40;
        assertThrows(RuntimeException.class, () ->
                operationHandler.updateNumberOffFruit(fruit, amount));
    }

    @AfterEach
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
