package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidQuantityException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private static final String BANANA = "banana";
    private static final OperationHandler operation
            = new BalanceOperationHandler(new FruitDaoImpl());

    @Test
    void apply_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put(BANANA, 30);
        operation.apply(new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 30));
        Map<String, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    void apply_valueLessThanZero_notOk() {
        assertThrows(InvalidQuantityException.class, () -> operation.apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, -30)));
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
