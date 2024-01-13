package core.basesyntax.services.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NegativeValueForOperationException;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.services.handlers.impl.BalanceOperationHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private static final FruitTransaction ORANGE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 10);
    private static final FruitTransaction APPLE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20);
    private static final FruitTransaction BANANA_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 30);
    private static final FruitTransaction TRANSACTION_WITH_NEGATIVE_QUANTITY =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", -1);
    private static final String EXPECTED_EXCEPTION_MESSAGE =
            "Balance operation value for banana should've been positive but was -1";
    private static BalanceOperationHandler balanceOperationHandler;

    @BeforeAll
    static void fillStorage() {
        balanceOperationHandler = new BalanceOperationHandler();

    }

    @AfterAll
    static void closeBalanceOperationHandler() {
        balanceOperationHandler = null;
    }

    @Test
    void handleOperation_tryPuttingNegativeQuantity_notOk() {
        RuntimeException negativeValueForOperationException =
                assertThrows(NegativeValueForOperationException.class,
                        () -> balanceOperationHandler.handleOperation(
                                TRANSACTION_WITH_NEGATIVE_QUANTITY));
        assertEquals(negativeValueForOperationException.getMessage(), EXPECTED_EXCEPTION_MESSAGE);
    }

    @Test
    void handleOperation_retrieveGoodDataAfterFunction_Ok() {
        balanceOperationHandler.handleOperation(ORANGE_FRUITTRANSACTION);
        balanceOperationHandler.handleOperation(APPLE_FRUITTRANSACTION);
        balanceOperationHandler.handleOperation(BANANA_FRUITTRANSACTION);
        assertTrue(Storage.getFruits().containsKey("orange"));
        assertTrue(Storage.getFruits().containsKey("apple"));
        assertTrue(Storage.getFruits().containsKey("banana"));
        assertTrue(Storage.getFruits().containsValue(10));
        assertTrue(Storage.getFruits().containsValue(20));
        assertTrue(Storage.getFruits().containsValue(30));
        assertEquals(10, Storage.getFruits().get("orange"));
        assertEquals(20, Storage.getFruits().get("apple"));
        assertEquals(30, Storage.getFruits().get("banana"));
    }

}
