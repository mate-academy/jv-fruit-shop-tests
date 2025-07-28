package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {

    private OperationHandler operationHandler = new SupplyOperation();

    @Test
    void supply_EmptyFruit_NotOk() {
        String fruit = "";
        int amount = 40;
        assertThrows(RuntimeException.class, () ->
                operationHandler.updateNumberOffFruit(fruit, amount));
    }

    @Test
    void supply_ZeroBalance_NotOk() {
        String fruit = "banana";
        int amount = 0;
        assertThrows(RuntimeException.class, () ->
                operationHandler.updateNumberOffFruit(fruit, amount));
    }

    @Test
    void supply_CheckForNoExceptions_Ok() {
        String fruit = "orange";
        int amount = 40;
        String expected = "40";
        operationHandler.updateNumberOffFruit(fruit, amount);
        assertEquals(expected, String.valueOf(Storage.storage.get(fruit)));
    }

    @Test
    void supply_NullFruit_NotOk() {
        String fruit = null;
        int amount = 200;
        assertThrows(RuntimeException.class, () ->
                operationHandler.updateNumberOffFruit(fruit, amount));
    }

    @Test
    void supply_MinusAmount_NotOk() {
        String fruit = "pineapple";
        int amount = -100;
        assertThrows(RuntimeException.class, () ->
                operationHandler.updateNumberOffFruit(fruit, amount));
    }

    @Test
    void supply_FruitNameStartedWithSpeciallySymbols_NotOk() {
        String fruit = "$%%#^#^@#^";
        int amount = 120;
        assertThrows(RuntimeException.class, () ->
                operationHandler.updateNumberOffFruit(fruit, amount));
    }

    @AfterEach
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
