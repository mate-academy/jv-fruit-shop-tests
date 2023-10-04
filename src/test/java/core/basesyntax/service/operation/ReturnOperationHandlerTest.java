package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static OperationHandler operationBalanceHandler;
    private static FruitTransaction fruitTransactionBalanceBanana;
    private static FruitTransaction fruitTransactionReturnBanana;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new ReturnOperationHandler();
        operationBalanceHandler = new BalanceOperationHandler();
        fruitTransactionBalanceBanana =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
        fruitTransactionReturnBanana =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 5);
    }

    @BeforeEach
    void setUp() {
        operationBalanceHandler.operateTransaction(fruitTransactionBalanceBanana);
    }

    @AfterEach
    void tearDown() {
        Storage.totalFruit.clear();
    }

    @Test
    void operateTransaction_transactionIsNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> operationHandler.operateTransaction(null));
    }

    @Test
    void operateTransaction_transactionIsCorrect_ok() {
        int initialQuantity = fruitTransactionBalanceBanana.getQuantity();
        int returnQuantity = fruitTransactionReturnBanana.getQuantity();
        operationHandler.operateTransaction(fruitTransactionReturnBanana);
        int quantityAfterReturn = Storage.totalFruit.get("banana");
        assertEquals(initialQuantity + returnQuantity, quantityAfterReturn);
    }
}
