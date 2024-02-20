package model;

import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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