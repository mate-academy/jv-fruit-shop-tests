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
    void test_countQuantity_ok() {
        assertEquals(5,operationHandler.countQuantity(20,15));
        assertEquals(87,operationHandler.countQuantity(100,13));
    }

    @Test
    void test_countQuantity_negativeOperationAmount_notOk() {
        FruitTransactionException exception = assertThrows(FruitTransactionException.class,
                () -> operationHandler.countQuantity(10, -15));
        assertEquals("operation amount can not be less than 0",exception.getMessage());
    }

    @Test
    void test_countQuantity_negativeCurrentAmount() {
        FruitTransactionException exception = assertThrows(FruitTransactionException.class,
                () -> operationHandler.countQuantity(-15, 11));
        assertEquals("current amount can not be less than 0",exception.getMessage());
    }

    @Test
    void test_countQuantity_negativeResult() {
        FruitTransactionException exception = assertThrows(FruitTransactionException.class,
                () -> operationHandler.countQuantity(10, 11));
        assertEquals("balance can not be less than 0",exception.getMessage());
    }

}
