package core.basesyntax.service.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.enums.Operation;
import java.util.Map;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest extends BaseOperationHandlerTest {
    @Test
    void handle_supplyFruitTransaction_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, BANANA, 20);
        handleTransaction(transaction);
        assertEquals(Map.of(BANANA, 20), db);
    }
}
