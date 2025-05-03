package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String BALANCE_CODE = "b";
    private static final String PURCHASE_CODE = "p";
    private static final String SUPPLY_CODE = "s";
    private static final String RETURN_CODE = "r";
    private static final String UNKNOWN_CODE = "rr";
    private static final String EMPTY_INPUT = "";

    @Test
    void fromCode_validDataInput_ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual = FruitTransaction.Operation.fromCode(BALANCE_CODE);
        assertEquals(expected, actual);

        expected = FruitTransaction.Operation.PURCHASE;
        actual = FruitTransaction.Operation.fromCode(PURCHASE_CODE);
        assertEquals(expected, actual);

        expected = FruitTransaction.Operation.SUPPLY;
        actual = FruitTransaction.Operation.fromCode(SUPPLY_CODE);
        assertEquals(expected, actual);

        expected = FruitTransaction.Operation.RETURN;
        actual = FruitTransaction.Operation.fromCode(RETURN_CODE);
        assertEquals(expected, actual);
    }

    @Test
    void fromCode_notValidDataInput_not0k() {
        assertThrows(IllegalArgumentException.class, () ->
                FruitTransaction.Operation.fromCode(UNKNOWN_CODE));
    }

    @Test
    void fromCode_emptyInput_not0k() {
        assertThrows(IllegalArgumentException.class, () ->
                FruitTransaction.Operation.fromCode(EMPTY_INPUT));
    }
}
