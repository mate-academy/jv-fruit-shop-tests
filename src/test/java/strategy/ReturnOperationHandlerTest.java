package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {
    private static final String BANANA = "banana";
    private static final OperationHandler operation
            = new ReturnOperationHandler(new FruitDaoImpl());

    @BeforeEach
    void setUp() {
        Storage.fruits.put(BANANA,30);
    }

    @Test
    void apply_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put(BANANA, 60);
        operation.apply(new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA, 30));
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
