package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private OperationHandler operationHandler = new BalanceOperationHandler();
    private Map<String, Integer> expected = new HashMap<>();
    private Map<String, Integer> actual = new HashMap<>();

    @Test
    void getSupplyWithBanana_Ok() {
        expected.put("banana", 50);
        FruitTransaction banana = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, "banana", 50);
        operationHandler.handle(banana);
        actual = Storage.FRUITS;

        assertEquals(expected, actual, "There's an error during processing the operation BALANCE");
    }

    @Test
    void getBalanceWithOrange_Ok() {
        expected.put("orange", 100);
        FruitTransaction orange = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, "orange", 100);
        operationHandler.handle(orange);
        actual = Storage.FRUITS;

        assertEquals(expected, actual, "There's an error during processing the operation BALANCE");
    }

    @Test
    void getBalanceWithNewFruit_Ok() {
        expected.put("new fruit", 0);
        FruitTransaction newFruit = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, "new fruit", 0);
        operationHandler.handle(newFruit);
        actual = Storage.FRUITS;

        assertEquals(expected, actual, "There's an error during processing the operation BALANCE");
    }

    @AfterEach
    void clearStorage() {
        Storage.FRUITS.clear();
    }
}
