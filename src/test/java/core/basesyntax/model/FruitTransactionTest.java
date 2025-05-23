package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private String code;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);
    }

    @Test
    void getOperation_methodResultCoincideWithFieldValue_Ok() {
        FruitTransaction.Operation expectedOperation = FruitTransaction.Operation.BALANCE;
        assertEquals(expectedOperation,fruitTransaction.getOperation());
    }

    @Test
    void getFruit_methodResultCoincideWithFieldValue_Ok() {
        String expectedFruit = "banana";
        assertEquals(expectedFruit,fruitTransaction.getFruit());
    }

    @Test
    void getQuantity_methodResultCoincideWithFieldValue_Ok() {
        int expectedQuantity = 100;
        assertEquals(expectedQuantity,fruitTransaction.getQuantity());
    }

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

    @Test
    void setOperation_setOperationResultIsValid_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        assertEquals(FruitTransaction.Operation.PURCHASE, fruitTransaction.getOperation());
    }
}
