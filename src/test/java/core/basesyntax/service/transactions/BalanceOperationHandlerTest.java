package core.basesyntax.service.transactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.FruitTransactionException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    void countQuantity_validQuantity_ok() {
        int firstExpectedQuantity = 15;
        assertEquals(firstExpectedQuantity,operationHandler.countQuantity(0,15));
        int secondExpectedQuantity = 113;
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
    void countQuantity_negativeCurrentAmount() {
        FruitTransactionException exception = assertThrows(FruitTransactionException.class,
                () -> operationHandler.countQuantity(-15, 11));
        String expected = "current amount can not be less than 0";
        assertEquals(expected,exception.getMessage());
    }

}
