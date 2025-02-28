package core.basesyntax.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void operationFromCodeValid_Ok() {
        Map<String, FruitTransaction.Operation> testCases = Map.of(
                "b", FruitTransaction.Operation.BALANCE,
                "s", FruitTransaction.Operation.SUPPLY,
                "p", FruitTransaction.Operation.PURCHASE,
                "r", FruitTransaction.Operation.RETURN
        );

        for (Map.Entry<String, FruitTransaction.Operation> entry : testCases.entrySet()) {
            assertEquals(entry.getValue(), FruitTransaction.Operation.fromCode(entry.getKey()));
        }
    }

    @Test
    void operationFromCodeInvalid_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("x"));
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode(null));
    }
}
