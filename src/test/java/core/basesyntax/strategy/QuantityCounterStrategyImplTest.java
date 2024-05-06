package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.BalanceQuantityCounter;
import core.basesyntax.service.QuantityCounter;
import core.basesyntax.transaction.Transaction;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class QuantityCounterStrategyImplTest {
    private static QuantityCounterStrategy validStrategy;
    private static QuantityCounterStrategy nullMapStrategy;
    private static QuantityCounterStrategy emptyMapStrategy;
    private static Transaction validTransaction;

    @BeforeAll
    static void beforeAll() {
        validStrategy = new QuantityCounterStrategyImpl(new QuantityCountersMap()
                    .getQuantityCountersMap());
        nullMapStrategy = new QuantityCounterStrategyImpl(null);
        emptyMapStrategy = new QuantityCounterStrategyImpl(new HashMap<>());
        validTransaction = new Transaction();
        validTransaction.setOperation(Transaction.Operation.BALANCE);
        validTransaction.setProduct("apple");
        validTransaction.setQuantity(20);
    }

    @Test
    void get_validStrategy_ok() {
        QuantityCounter actual = validStrategy.get(validTransaction);
        assertTrue(actual instanceof BalanceQuantityCounter,
                "If operation is Balance, "
                + "BalanceQuantityCounter should be returned");
    }

    @Test
    void get_nullMapStrategy_notOk() {
        NullPointerException actual = assertThrows(NullPointerException.class,
                () -> nullMapStrategy.get(validTransaction),
                "If Map is null, exception should be thrown");
        assertEquals("Map can't be null", actual.getMessage());
    }

    @Test
    void get_emptyMapStrategy_notOk() {
        IllegalArgumentException actual = assertThrows(IllegalArgumentException.class,
                () -> emptyMapStrategy.get(validTransaction),
                "if QuantityCountersMap is empty, "
                        + "exception should be thrown");
        assertEquals("Map can't be empty", actual.getMessage());
    }

    @Test
    void get_nullInputTransaction_notOk() {
        IllegalArgumentException actual = assertThrows(IllegalArgumentException.class,
                () -> validStrategy.get(null),
                "If input Transaction is null, exception should be thrown");
        assertEquals("Transaction can't be null", actual.getMessage());
    }
}
