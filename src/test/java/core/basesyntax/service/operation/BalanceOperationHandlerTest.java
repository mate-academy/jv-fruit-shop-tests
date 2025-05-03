package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransactionBanana;
    private static FruitTransaction fruitTransactionApple;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new BalanceOperationHandler();
        fruitTransactionBanana =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 15);
        fruitTransactionApple =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20);
    }

    @Test
    void operateTransaction_transactionIsNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> operationHandler.operateTransaction(null));
    }

    @Test
    void operateTransaction_balance_ok() {
        operationHandler.operateTransaction(fruitTransactionBanana);
        assertEquals(Storage.totalFruit.get("banana"), 15);
    }
}
