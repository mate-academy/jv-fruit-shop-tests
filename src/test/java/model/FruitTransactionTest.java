package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final Map<String, String> trueCodeOperations = Map.of(
            "BALANCE", "b", "SUPPLY", "s", "PURCHASE", "p", "RETURN", "r");

    @Test
    void validation_Ok() {
        for (Map.Entry<String, String> entry : trueCodeOperations.entrySet()) {
            assertEquals(FruitTransaction.Operation
                    .validation(entry.getValue()).name(), entry.getKey());
        }
    }

    @Test
    void validation_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            FruitTransaction.Operation.validation("badCode");
        });
    }
}
