package core.basesyntax.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransactionBalanceBanana;
    private static FruitTransaction fruitTransactionBalanceApple;
    private static final int QUANTITY_BANANA = 20;
    private static final int QUANTITY_APPLE = 10;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new BalanceOperationHandler();
        fruitTransactionBalanceBanana
                = new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana",20);
        fruitTransactionBalanceApple
                = new FruitTransaction(FruitTransaction.Operation.BALANCE,"apple",10);
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void processTransaction_transactionIsCorrect_Ok() {
        operationHandler.processTransaction(fruitTransactionBalanceBanana);
        assertEquals(Storage.storage.get("banana"), QUANTITY_BANANA);
        operationHandler.processTransaction(fruitTransactionBalanceApple);
        assertEquals(Storage.storage.get("apple"), QUANTITY_APPLE);
    }

    @Test
    void processTransaction_transactionIsNull_NotOk() {
        assertThrows(RuntimeException.class,() -> operationHandler.processTransaction(null));
    }
}
