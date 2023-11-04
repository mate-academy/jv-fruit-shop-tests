package core.basesyntax.service.transactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.FruitTransactionException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Test
    void countQuantity_validQuantity_ok() {
        int firstExpectedQuantity = 5;
        assertEquals(firstExpectedQuantity,operationHandler.countQuantity(20,15));
        int secondExpectedQuantity = 87;
        assertEquals(secondExpectedQuantity,operationHandler.countQuantity(100,13));
    }

    @Test
    void countQuantity_negativeOperationAmount_notOk() {
        FruitTransactionException exception = assertThrows(FruitTransactionException.class,
                () -> operationHandler.countQuantity(10, -15));
        String expected = "operation amount can not be less than 0";
        assertEquals(expected,exception.getMessage());
    }

    @Test
    void countQuantity_negativeCurrentAmount_notOk() {
        FruitTransactionException exception = assertThrows(FruitTransactionException.class,
                () -> operationHandler.countQuantity(-15, 11));
        String expected = "current amount can not be less than 0";
        assertEquals(expected,exception.getMessage());
    }

    @Test
    void countQuantity_negativeResult_notOk() {
        FruitTransactionException exception = assertThrows(FruitTransactionException.class,
                () -> operationHandler.countQuantity(10, 11));
        String expected = "balance can not be less than 0";
        assertEquals(expected,exception.getMessage());
    }

}
