package core.basesyntax.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void correctReturnFor_balanceOperation() {
        FruitTransaction.Operation b = FruitTransaction.Operation.fromCode("b");
        assertEquals(b, FruitTransaction.Operation.BALANCE);
    }

    @Test
    void correctReturnFor_purchaseOperation() {
        FruitTransaction.Operation p = FruitTransaction.Operation.fromCode("p");
        assertEquals(p, FruitTransaction.Operation.PURCHASE);
    }

    @Test
    void correctReturnFor_returnOperation() {
        FruitTransaction.Operation r = FruitTransaction.Operation.fromCode("r");
        assertEquals(r, FruitTransaction.Operation.RETURN);
    }

    @Test
    void correctReturnFor_supplyOperation() {
        FruitTransaction.Operation s = FruitTransaction.Operation.fromCode("s");
        assertEquals(s, FruitTransaction.Operation.SUPPLY);
    }

    @Test
    void throwsExceptionForInvalidCode() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> {
                FruitTransaction.Operation.fromCode("k");
            });
        assertEquals("Unknown code: k", exception.getMessage());
    }
}
