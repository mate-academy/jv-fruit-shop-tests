package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final OperationHandler operation
            = new SupplyOperationHandler(new FruitDaoImpl());

    @BeforeEach
    void setUp() {
        Storage.fruits.put(BANANA,30);
    }

    @Test
    void apply_supplySameFruit_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put(BANANA, 60);
        operation.apply(new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 30));
        Map<String, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    void apply_supplyNewFruit_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put(BANANA, 30);
        expected.put(APPLE, 10);
        operation.apply(new FruitTransaction(FruitTransaction.Operation.SUPPLY, APPLE, 10));
        Map<String, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    void apply_fruitTransactionNull_notOk() {
        assertThrows(NullPointerException.class, () -> operation.apply(null));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
