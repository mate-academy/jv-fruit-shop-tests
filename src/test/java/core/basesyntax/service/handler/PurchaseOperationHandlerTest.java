package core.basesyntax.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static OperationHandler operationBalanceHandler;
    private static FruitTransaction fruitTransactionBalanceBanana;
    private static FruitTransaction fruitTransactionBalanceApple;
    private static FruitTransaction fruitTransactionPurchaseBanana;
    private static FruitTransaction fruitTransactionPurchaseApple;
    private static final int QUANTITY_BANANA = 10;
    private static final int QUANTITY_APPLE = 5;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new PurchaseOperationHandler();
        operationBalanceHandler = new BalanceOperationHandler();
        fruitTransactionBalanceBanana
                = new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana",20);
        fruitTransactionBalanceApple
                = new FruitTransaction(FruitTransaction.Operation.BALANCE,"apple",10);
        fruitTransactionPurchaseBanana
                = new FruitTransaction(FruitTransaction.Operation.PURCHASE,"banana",10);
        fruitTransactionPurchaseApple
                = new FruitTransaction(FruitTransaction.Operation.PURCHASE,"apple",5);
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
        operationHandler.processTransaction(fruitTransactionPurchaseBanana);
        assertEquals(Storage.storage.get("banana"), QUANTITY_BANANA);
        operationHandler.processTransaction(fruitTransactionPurchaseApple);
        assertEquals(Storage.storage.get("apple"), QUANTITY_APPLE);
    }

    @Test
    void processTransaction_transactionIsNull_NotOk() {
        assertThrows(RuntimeException.class,() -> operationHandler.processTransaction(null));
    }
}
