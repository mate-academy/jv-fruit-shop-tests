package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.Storage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DecreaseStrategyTest {
    private static final String BANANA = "banana";
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new DecreaseStrategy(new FruitDaoImpl());
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void apply_validInput_ok() {
        Storage.STORAGE.put(BANANA, 20);
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.PURCHASE, BANANA, 10);
        operationHandler.apply(fruitTransaction);
        Map<String, Integer> expected = Map.of(BANANA, 10);
        Map<String, Integer> actual = Storage.STORAGE;
        assertEquals(expected, actual);
    }

    @Test
    void apply_invalidInput_notOk() {
        Storage.STORAGE.put(BANANA, 5);
        FruitTransaction invalidTransaction = new FruitTransaction(Operation.PURCHASE, BANANA, 10);
        assertThrows(IllegalArgumentException.class, () ->
                operationHandler.apply(invalidTransaction));
        operationHandler.apply(new FruitTransaction(Operation.PURCHASE, BANANA, 2));
    }
}
