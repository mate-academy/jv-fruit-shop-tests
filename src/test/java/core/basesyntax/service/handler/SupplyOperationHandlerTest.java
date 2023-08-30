package core.basesyntax.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static OperationHandler operationBalanceHandler;
    private static FruitTransaction fruitTransactionBalanceBanana;
    private static FruitTransaction fruitTransactionBalanceApple;
    private static FruitTransaction fruitTransactionSupplyBanana;
    private static FruitTransaction fruitTransactionSupplyApple;
    private static final int QUANTITY_BANANA = 30;
    private static final int QUANTITY_APPLE = 15;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new ReturnOperationHandler();
        operationBalanceHandler = new BalanceOperationHandler();
        fruitTransactionBalanceBanana
                = new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana",20);
        fruitTransactionBalanceApple
                = new FruitTransaction(FruitTransaction.Operation.BALANCE,"apple",10);
        fruitTransactionSupplyBanana
                = new FruitTransaction(FruitTransaction.Operation.RETURN,"banana",10);
        fruitTransactionSupplyApple
                = new FruitTransaction(FruitTransaction.Operation.RETURN,"apple",5);
    }

    @BeforeEach
    void setUp() {
        operationBalanceHandler.processTransaction(fruitTransactionBalanceBanana);
        operationBalanceHandler.processTransaction(fruitTransactionBalanceApple);
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void processTransaction_transactionIsCorrect_Ok() {
        operationHandler.processTransaction(fruitTransactionSupplyBanana);
        assertEquals(QUANTITY_BANANA, Storage.storage.get("banana"));
        operationHandler.processTransaction(fruitTransactionSupplyApple);
        assertEquals(QUANTITY_APPLE,Storage.storage.get("apple"));

    }

    @Test
    void processTransaction_transactionIsNull_NotOk() {
        assertThrows(RuntimeException.class,() -> operationHandler.processTransaction(null));
    }
}
