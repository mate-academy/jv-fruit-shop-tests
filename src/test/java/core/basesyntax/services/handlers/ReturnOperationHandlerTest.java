package core.basesyntax.services.handlers;

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
    public static final FruitTransaction ORANGE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, Constants.ORANGE, 10);
    public static final FruitTransaction APPLE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, Constants.APPLE, 10);
    public static final FruitTransaction BANANA_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, Constants.BANANA, 10);
    public static final FruitTransaction BANANA_FRUITTRANSACTION_NEGATIVE_QUANTITY =
            new FruitTransaction(FruitTransaction.Operation.RETURN, Constants.BANANA, -1);
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
        Storage.updateFruit(Constants.ORANGE, Constants.INITIAL_QUANTITY_OF_ORANGE);
        Storage.updateFruit(Constants.APPLE, Constants.INITIAL_QUANTITY_OF_APPLE);
        Storage.updateFruit(Constants.BANANA, Constants.INITIAL_QUANTITY_OF_BANANA);
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
        assertEquals(EXPECTED_RESULT_QUANTITY_OF_ORANGE, Storage.getFruits().get(Constants.ORANGE));
        assertEquals(EXPECTED_RESULT_QUANTITY_OF_APPLE, Storage.getFruits().get(Constants.APPLE));
        assertEquals(EXPECTED_RESULT_QUANTITY_OF_BANANA, Storage.getFruits().get(Constants.BANANA));
    }
}
