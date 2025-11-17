package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static final String FRUIT = "apple";

    @BeforeEach
    void resetStorage() {
        Storage.put(FRUIT, null);
    }

    @Test
    void positiveBalanceHandler() {
        Storage.put(FRUIT, 50);

        BalanceHandler handler = new BalanceHandler();
        handler.apply(new FruitTransaction(Operation.BALANCE, FRUIT, 20));

        assertEquals(Integer.valueOf(20), Storage.getOrDefault(FRUIT, 0));
    }
}
