package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.UnknownOperationException;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String BALANCE_OPERATION_CODE = "b";
    private static final String SUPPLY_OPERATION_CODE = "s";
    private static final String RETURN_OPERATION_CODE = "r";
    private static final String PURCHASE_OPERATION_CODE = "p";
    private static final String UNKNOWN_OPERATION_CODE = "z";
    private static final FruitTransaction.Operation BALANCE_OPERATION
            = FruitTransaction.Operation.BALANCE;
    private static final FruitTransaction.Operation SUPPLY_OPERATION
            = FruitTransaction.Operation.SUPPLY;
    private static final FruitTransaction.Operation RETURN_OPERATION
            = FruitTransaction.Operation.RETURN;
    private static final FruitTransaction.Operation PURCHASE_OPERATION
            = FruitTransaction.Operation.PURCHASE;

    @Test
    void convertToOperation_isOK() {
        assertEquals(BALANCE_OPERATION,
                FruitTransaction.Operation.convertToOperation(BALANCE_OPERATION_CODE));
        assertEquals(SUPPLY_OPERATION,
                FruitTransaction.Operation.convertToOperation(SUPPLY_OPERATION_CODE));
        assertEquals(RETURN_OPERATION,
                FruitTransaction.Operation.convertToOperation(RETURN_OPERATION_CODE));
        assertEquals(PURCHASE_OPERATION,
                FruitTransaction.Operation.convertToOperation(PURCHASE_OPERATION_CODE));
    }

    @Test
    void convertToOperation_unknownOperationCode_notOk() {
        assertThrows(UnknownOperationException.class,
                () -> FruitTransaction.Operation.convertToOperation(UNKNOWN_OPERATION_CODE));
    }

    @Test
    void convertToOperation_nullValue_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.convertToOperation(null));
    }
}
