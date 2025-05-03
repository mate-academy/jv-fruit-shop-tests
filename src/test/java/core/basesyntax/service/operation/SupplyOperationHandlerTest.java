package core.basesyntax.service.operation;

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
    private static FruitTransaction fruitTransactionSupplyBanana;

    @BeforeAll
    static void beforeAll() {
        operationBalanceHandler = new BalanceOperationHandler();
        operationHandler = new SupplyOperationHandler();
        fruitTransactionBalanceBanana =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
        fruitTransactionSupplyBanana =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 5);
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
    void operateTransaction_transactionIsCorrect_ok() {
        int initialQuantity = fruitTransactionBalanceBanana.getQuantity();
        int purchaseQuantity = fruitTransactionSupplyBanana.getQuantity();
        operationHandler.operateTransaction(fruitTransactionSupplyBanana);
        int quantityAfterPurchase = Storage.totalFruit.get("banana");
        assertEquals(initialQuantity + purchaseQuantity, quantityAfterPurchase);
    }
}
