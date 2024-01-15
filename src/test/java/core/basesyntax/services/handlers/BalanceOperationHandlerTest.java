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
            new FruitTransaction(FruitTransaction.Operation.BALANCE, Constants.ORANGE, 10);
    private static final FruitTransaction APPLE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, Constants.APPLE, 10);
    private static final FruitTransaction BANANA_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, Constants.BANANA, 10);
    private static final FruitTransaction BANANA_FRUITTRANSACTION_NEGATIVE_QUANTITY =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, Constants.BANANA, -1);
    private static final int EXPECTED_QUANTITY_OF_ORANGE = 10;
    private static final int EXPECTED_QUANTITY_OF_APPLE = 10;
    private static final int EXPECTED_QUANTITY_OF_BANANA = 10;
    private static final String EXPECTED_EXCEPTION_MESSAGE =
            "Balance operation value for banana should've been positive but was " +
                    BANANA_FRUITTRANSACTION_NEGATIVE_QUANTITY.getQuantity();
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
                                BANANA_FRUITTRANSACTION_NEGATIVE_QUANTITY));
        assertEquals(negativeValueForOperationException.getMessage(), EXPECTED_EXCEPTION_MESSAGE);
    }

    @Test
    void handleOperation_retrieveGoodDataAfterFunction_Ok() {
        balanceOperationHandler.handleOperation(ORANGE_FRUITTRANSACTION);
        balanceOperationHandler.handleOperation(APPLE_FRUITTRANSACTION);
        balanceOperationHandler.handleOperation(BANANA_FRUITTRANSACTION);
        assertTrue(Storage.getFruits().containsKey(Constants.ORANGE));
        assertTrue(Storage.getFruits().containsKey(Constants.APPLE));
        assertTrue(Storage.getFruits().containsKey(Constants.BANANA));
        assertTrue(Storage.getFruits().containsValue(EXPECTED_QUANTITY_OF_ORANGE));
        assertTrue(Storage.getFruits().containsValue(EXPECTED_QUANTITY_OF_APPLE));
        assertTrue(Storage.getFruits().containsValue(EXPECTED_QUANTITY_OF_BANANA));
        assertEquals(EXPECTED_QUANTITY_OF_ORANGE, Storage.getFruits().get(Constants.ORANGE));
        assertEquals(EXPECTED_QUANTITY_OF_APPLE, Storage.getFruits().get(Constants.APPLE));
        assertEquals(EXPECTED_QUANTITY_OF_BANANA, Storage.getFruits().get(Constants.BANANA));
    }

}
