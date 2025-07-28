package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

public class BalanceOperationTest {
    private OperationHandler operationHandler = new BalanceOperation();

    @Test
    public void balance_NullFruit_NotOk() {
        assertThrows(RuntimeException.class, () ->
                operationHandler.updateNumberOffFruit(null, 100));
    }

    @Test
    public void balance_ZeroBalance_NotOk() {
        String fruit = "banana";
        int amount = 0;
        assertThrows(RuntimeException.class, () ->
                operationHandler.updateNumberOffFruit(fruit, amount));
    }

    @Test
    public void balance_EmptyFruit_NotOk() {
        String fruit = "";
        int amount = 40;
        assertThrows(RuntimeException.class, () ->
                operationHandler.updateNumberOffFruit(fruit, amount));
    }

    @Test
    public void balance_BalanceCheckForNoExceptions_Ok() {
        String fruit = "apple";
        int amount = 40;
        String expected = "40";
        operationHandler.updateNumberOffFruit(fruit, amount);
        assertEquals(expected, String.valueOf(Storage.storage.get(fruit)));
    }

    @Test
    public void balance_FruitNameStartedWithSpeciallySymbols_NotOk() {
        String fruit = "$%%#^#^@#@^";
        int amount = 5;
        assertThrows(RuntimeException.class, () ->
                operationHandler.updateNumberOffFruit(fruit, amount));
    }

    @AfterEach
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
