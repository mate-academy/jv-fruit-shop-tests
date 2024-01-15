package core.basesyntax.services.handlers;

import static core.basesyntax.services.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NegativeValueForOperationException;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.services.handlers.impl.ReturnOperationHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {
    private static final FruitTransaction ORANGE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, ORANGE, 10);
    private static final FruitTransaction APPLE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, APPLE, 10);
    private static final FruitTransaction BANANA_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA, 10);
    private static final FruitTransaction BANANA_FRUITTRANSACTION_NEGATIVE_QUANTITY =
            new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA, -1);
    private static final int EXPECTED_RESULT_QUANTITY_OF_ORANGE = 110;
    private static final int EXPECTED_RESULT_QUANTITY_OF_APPLE = 110;
    private static final int EXPECTED_RESULT_QUANTITY_OF_BANANA = 110;
    private static final String EXPECTED_EXCEPTION_MESSAGE_NEGATIVE_NUMBER_RETURN =
            "Return operation value for banana should've been positive but was " +
                    BANANA_FRUITTRANSACTION_NEGATIVE_QUANTITY.getQuantity();
    private static OperationHandler returnOperationHandler;

    @BeforeAll
    static void initReturnOperationHandlerAndStorage() {
        returnOperationHandler = new ReturnOperationHandler();
        Storage.updateFruit(ORANGE, INITIAL_QUANTITY_OF_ORANGE);
        Storage.updateFruit(APPLE, INITIAL_QUANTITY_OF_APPLE);
        Storage.updateFruit(BANANA, INITIAL_QUANTITY_OF_BANANA);
    }

    @AfterAll
    static void closeReturnOperationHandler() {
        returnOperationHandler = null;
    }

    @Test
    void handleOperation_returnNegativeQuantity_notOk() {
        RuntimeException negativeValueForOperationException =
                assertThrows(NegativeValueForOperationException.class,
                        () -> returnOperationHandler.handleOperation(
                                BANANA_FRUITTRANSACTION_NEGATIVE_QUANTITY));
        assertEquals(negativeValueForOperationException.getMessage(),
                EXPECTED_EXCEPTION_MESSAGE_NEGATIVE_NUMBER_RETURN);
    }

    @Test
    void handleOperation_goodTransactions_Ok() {
        returnOperationHandler.handleOperation(ORANGE_FRUITTRANSACTION);
        returnOperationHandler.handleOperation(APPLE_FRUITTRANSACTION);
        returnOperationHandler.handleOperation(BANANA_FRUITTRANSACTION);
        assertEquals(EXPECTED_RESULT_QUANTITY_OF_ORANGE, Storage.getFruits().get(ORANGE));
        assertEquals(EXPECTED_RESULT_QUANTITY_OF_APPLE, Storage.getFruits().get(APPLE));
        assertEquals(EXPECTED_RESULT_QUANTITY_OF_BANANA, Storage.getFruits().get(BANANA));
    }
}
