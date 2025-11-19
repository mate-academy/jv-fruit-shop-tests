package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private static final String FRUIT = "apple";

    @BeforeEach
    void resetStorage() {
        Storage.put(FRUIT, null);
    }

    @Test
    void supplyAddAmount() {
        Storage.put(FRUIT, 10);

        SupplyHandler handler = new SupplyHandler();
        handler.apply(new FruitTransaction(Operation.SUPPLY, FRUIT, 15));

        assertEquals(Integer.valueOf(25), Storage.getOrDefault(FRUIT, 0));
    }
}
