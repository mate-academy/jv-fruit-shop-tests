package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private String code;

    @Test
    void getOperation_codeCorrespondsToOperation_Ok() {
        FruitTransaction.Operation expectedBalanceOperation = FruitTransaction
                .Operation.BALANCE;
        code = "b";
        FruitTransaction.Operation actualBalanceOperation = FruitTransaction
                .Operation.getOperation(code);
        assertEquals(expectedBalanceOperation, actualBalanceOperation);

        FruitTransaction.Operation expectedPurchaseOperation = FruitTransaction
                .Operation.PURCHASE;
        code = "p";
        FruitTransaction.Operation actualPurchaseOperation = FruitTransaction
                .Operation.getOperation(code);
        assertEquals(expectedPurchaseOperation, actualPurchaseOperation);

        FruitTransaction.Operation expectedSupplyOperation = FruitTransaction
                .Operation.SUPPLY;
        code = "s";
        FruitTransaction.Operation actualSupplyOperation = FruitTransaction
                .Operation.getOperation(code);
        assertEquals(expectedSupplyOperation, actualSupplyOperation);

        FruitTransaction.Operation expectedReturnOperation = FruitTransaction
                .Operation.RETURN;
        code = "r";
        FruitTransaction.Operation actualReturnOperation = FruitTransaction
                .Operation.getOperation(code);
        assertEquals(expectedReturnOperation, actualReturnOperation);
    }

    @Test
    void getOperation_codeIsInvalid_NotOk() {
        code = "t";
        assertThrows(RuntimeException.class, () -> FruitTransaction
                .Operation.getOperation(code));
    }
}
