package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private FruitTransaction transaction1;

    @BeforeEach
    void setUp() {
        transaction1 = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "APPLE", 1);
    }

    @Test
    void toString_AllOperations_Ok() {
        FruitTransaction t1 = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "APPLE", 1);
        String expected1 = "FruitTransaction{operation=PURCHASE, fruit='APPLE', quantity=1}";
        assertEquals(expected1, t1.toString());

        FruitTransaction t2 = new FruitTransaction(FruitTransaction.Operation.BALANCE, "BANANA", 5);
        String expected2 = "FruitTransaction{operation=BALANCE, fruit='BANANA', quantity=5}";
        assertEquals(expected2, t2.toString());

        FruitTransaction t3 = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "ORANGE", 3);
        String expected3 = "FruitTransaction{operation=SUPPLY, fruit='ORANGE', quantity=3}";
        assertEquals(expected3, t3.toString());

        FruitTransaction t4 = new FruitTransaction(FruitTransaction.Operation.RETURN, "GRAPE", 2);
        String expected4 = "FruitTransaction{operation=RETURN, fruit='GRAPE', quantity=2}";
        assertEquals(expected4, t4.toString());
    }

    @Test
    void fromCode_exhaustiveErrorPath_Ok() {
        String invalidCode = "z";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode(invalidCode));
        assertEquals("Invalid code: " + invalidCode, exception.getMessage());
    }

    @Test
    void fromCode_validCode_Ok() {
        assertEquals(transaction1.getOperation(), FruitTransaction.Operation.fromCode("p"));
    }

    @Test
    void fromCode_completePathCoverage_Ok() {
        for (FruitTransaction.Operation op : FruitTransaction.Operation.values()) {
            assertEquals(op, FruitTransaction.Operation.fromCode(op.getCode()));
        }
    }

    @Test 
    void fromCode_invalidCodes_notOk() {
        String[] invalidCodes = {"x", "z", "q", "", "abc"};
        for (String invalidCode : invalidCodes) {
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                    () -> FruitTransaction.Operation.fromCode(invalidCode));
            assertEquals("Invalid code: " + invalidCode, e.getMessage());
        }
    }

    @Test
    void fromCode_nullCode_Ok() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode(null));
        assertTrue(exception.getMessage().startsWith("Invalid code:"));
    }

    @Test
    void fromCode_emptyCode_Ok() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode(""));
        assertTrue(exception.getMessage().startsWith("Invalid code:"));
    }
}
