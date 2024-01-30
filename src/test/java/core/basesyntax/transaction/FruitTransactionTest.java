package core.basesyntax.transaction;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FruitTransactionTest {

    @Test
    void testToStringWithNullValue() {
        FruitTransaction transaction = new FruitTransaction(null, null, 0);
        assertDoesNotThrow(() -> transaction.toString());
    }
}