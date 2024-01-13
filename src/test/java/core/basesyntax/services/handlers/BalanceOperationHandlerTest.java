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
    private static final String ORANGE = "orange";
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int EXPECTED_QUANTITY_OF_ORANGE = 10;
    private static final int EXPECTED_QUANTITY_OF_APPLE = 20;
    private static final int EXPECTED_QUANTITY_OF_BANANA = 30;
    private static final FruitTransaction ORANGE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 10);
    private static final FruitTransaction APPLE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20);
    private static final FruitTransaction BANANA_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 30);
    private static final FruitTransaction TRANSACTION_WITH_NEGATIVE_QUANTITY =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", -1);
    private static final String EXPECTED_EXCEPTION_MESSAGE =
            "Balance operation value for banana should've been positive but was "
            + TRANSACTION_WITH_NEGATIVE_QUANTITY.getQuantity();
    private static OperationHandler balanceOperationHandler;

    @BeforeAll
    static void initBalanceOperationHandler() {
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
        assertTrue(Storage.getFruits().containsKey(ORANGE));
        assertTrue(Storage.getFruits().containsKey(APPLE));
        assertTrue(Storage.getFruits().containsKey(BANANA));
        assertTrue(Storage.getFruits().containsValue(EXPECTED_QUANTITY_OF_ORANGE));
        assertTrue(Storage.getFruits().containsValue(EXPECTED_QUANTITY_OF_APPLE));
        assertTrue(Storage.getFruits().containsValue(EXPECTED_QUANTITY_OF_BANANA));
        assertEquals(EXPECTED_QUANTITY_OF_ORANGE, Storage.getFruits().get(ORANGE));
        assertEquals(EXPECTED_QUANTITY_OF_APPLE, Storage.getFruits().get(APPLE));
        assertEquals(EXPECTED_QUANTITY_OF_BANANA, Storage.getFruits().get(BANANA));
    }

}
