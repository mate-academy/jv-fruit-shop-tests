package core.basesyntax.strategy.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.Operation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final Operation VALID_OPERATION = Operation.PURCHASE;
    private static final String VALID_FRUIT = "apple";
    private static final int VALID_QUANTITY = 20;
    private static final int VALID_PURCHASE = 10;
    private static final int INVALID_PURCHASE = 50;
    private static OperationHandler purchaseHandler;

    @BeforeAll
    static void beforeAll() {
        purchaseHandler = new PurchaseOperationHandler();
        OperationHandler balance = new BalanceOperationHandler();
        balance.handle(new FruitTransaction(Operation.BALANCE, VALID_FRUIT, VALID_QUANTITY));
    }

    @AfterAll
    static void afterAll() {
        Storage.getStorage().clear();
    }

    @Test
    void checkHandleValidQuantity_Ok() {
        FruitTransaction transaction =
                new FruitTransaction(VALID_OPERATION, VALID_FRUIT, VALID_PURCHASE);
        purchaseHandler.handle(transaction);
        int expected = VALID_QUANTITY - VALID_PURCHASE;
        assertEquals(expected, Storage.getStorage().get(transaction.getFruit()));
    }

    @Test
    void checkHandleInvalidQuantity_NotOk() {
        FruitTransaction transaction =
                new FruitTransaction(VALID_OPERATION, VALID_FRUIT, INVALID_PURCHASE);
        assertThrows(RuntimeException.class, () -> purchaseHandler.handle(transaction));
    }
}
