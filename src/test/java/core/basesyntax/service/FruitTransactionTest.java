package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    private static final FruitTransaction.Operation VALID_OPERATION =
            FruitTransaction.Operation.BALANCE;
    private static final String INVALID_CODE = "e";
    private static final String VALID_CODE = "b";

    @Test
    void getOperation_validCode_Ok() {
        Assertions.assertEquals(FruitTransaction.Operation.getOperation(VALID_CODE),
                VALID_OPERATION);
    }

    @Test
    void getOperation_invalidCode_notOk() {
        RuntimeException operationException = assertThrows(RuntimeException.class,
                () -> FruitTransaction.Operation.getOperation(INVALID_CODE));
        assertEquals("Unknown operation code" + INVALID_CODE, operationException.getMessage());
    }
}
