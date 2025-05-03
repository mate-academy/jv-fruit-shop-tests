package core.basesyntax.service.operation;

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
    private static FruitTransaction fruitTransactionPurchaseBanana;
    private static FruitTransaction fruitTransactionNotEnoughPurchaseBanana;

    @BeforeAll
    static void beforeAll() {
        operationBalanceHandler = new BalanceOperationHandler();
        operationHandler = new PurchaseOperationHandler();
        fruitTransactionBalanceBanana =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
        fruitTransactionPurchaseBanana =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5);
        fruitTransactionNotEnoughPurchaseBanana =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 15);
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
        assertThrows(RuntimeException.class, () -> operationHandler.operateTransaction(null));
    }

    @Test
    void operateTransaction_transactionLessThanBalance_notOk() {
        assertThrows(RuntimeException.class,
                () -> operationHandler.operateTransaction(fruitTransactionNotEnoughPurchaseBanana));
    }

    @Test
    void operateTransaction_transactionIsCorrect_ok() {
        int initialQuantity = fruitTransactionBalanceBanana.getQuantity();
        int purchaseQuantity = fruitTransactionPurchaseBanana.getQuantity();
        operationHandler.operateTransaction(fruitTransactionPurchaseBanana);
        int quantityAfterPurchase = Storage.totalFruit.get("banana");
        assertEquals(initialQuantity - purchaseQuantity, quantityAfterPurchase);
    }
}
