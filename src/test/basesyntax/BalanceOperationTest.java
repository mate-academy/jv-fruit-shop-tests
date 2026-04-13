import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;

import core.basesyntax.service.impl.BalanceOperation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BalanceOperationTest {

    private final BalanceOperation operation = new BalanceOperation();

    @Test
    void shouldSetInitialValue() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 50);

        FruitTransaction tx = new FruitTransaction();
        tx.setFruit("apple");
        tx.setQuantity(100);

        operation.process(tx, storage);

        assertEquals(100, storage.get("apple"));
    }
}
