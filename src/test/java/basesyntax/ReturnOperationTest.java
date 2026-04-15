package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ReturnOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {

    private final ReturnOperation operation = new ReturnOperation();

    @Test
    void shouldIncreaseStock() {
        Map<String, Integer> storage = new HashMap<>();

        FruitTransaction tx = new FruitTransaction();
        tx.setFruit("banana");
        tx.setQuantity(10);

        operation.process(tx, storage);

        assertEquals(10, storage.get("banana"));
    }
}
