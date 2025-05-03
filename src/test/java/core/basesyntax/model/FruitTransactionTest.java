package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    @Test
    void getFromCode_getBalanceFromCode_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.getFromCode("b");
        assertEquals(FruitTransaction.Operation.BALANCE, operation);
    }

    @Test
    void getFromCode_getSupplyFromCode_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.getFromCode("s");
        assertEquals(FruitTransaction.Operation.SUPPLY, operation);
    }

    @Test
    void getFromCode_getPurchaseFromCode_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.getFromCode("p");
        assertEquals(FruitTransaction.Operation.PURCHASE, operation);
    }

    @Test
    void getFromCode_getReturnFromCode_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.getFromCode("r");
        assertEquals(FruitTransaction.Operation.RETURN, operation);
    }

    @Test
    void getFromCode_getOperationFromInvalidCode_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.getFromCode("a"));
    }

    @Test
    void getFromCode_getOperationFromNullCode_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.getFromCode(null));
    }

    @Test
    void getFromCode_getOperationFromEmptyCode_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.getFromCode(""));
    }
}
