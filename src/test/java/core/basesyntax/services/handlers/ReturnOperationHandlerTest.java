package core.basesyntax.services.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NegativeValueForOperationException;
import core.basesyntax.exceptions.NoSuchFruitException;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.services.handlers.impl.ReturnOperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utilities.Constants;

public class ReturnOperationHandlerTest {
    private static final FruitTransaction ORANGE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, Constants.ORANGE, 10);
    private static final FruitTransaction APPLE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, Constants.APPLE, 10);
    private static final FruitTransaction BANANA_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, Constants.BANANA, 10);
    private static final FruitTransaction BANANA_FRUITTRANSACTION_NEGATIVE_QUANTITY =
            new FruitTransaction(FruitTransaction.Operation.RETURN, Constants.BANANA, -1);
    private static final int EXPECTED_RESULT_QUANTITY_OF_ORANGE = 110;
    private static final int EXPECTED_RESULT_QUANTITY_OF_APPLE = 110;
    private static final int EXPECTED_RESULT_QUANTITY_OF_BANANA = 110;
    private static final String EXPECTED_EXCEPTION_MESSAGE_NEGATIVE_NUMBER_RETURN =
            "Return operation value for banana should've been positive but was "
                    + BANANA_FRUITTRANSACTION_NEGATIVE_QUANTITY.getQuantity();
    private static final String EXPECTED_NO_SUCH_FRUIT_EXCEPTION_MESSAGE =
            "Fruit was not found in the storage: "
            + Constants.NO_SUCH_FRUIT_EXCEPTION_TRANSACTION.getFruit();
    private static OperationHandler returnOperationHandler;

    @BeforeAll
    static void initReturnOperationHandlerAndStorage() {
        returnOperationHandler = new ReturnOperationHandler();
        Storage.updateFruit(Constants.ORANGE, Constants.INITIAL_QUANTITY_OF_ORANGE);
        Storage.updateFruit(Constants.APPLE, Constants.INITIAL_QUANTITY_OF_APPLE);
        Storage.updateFruit(Constants.BANANA, Constants.INITIAL_QUANTITY_OF_BANANA);
    }

    @Test
    void handleOperation_tryRetrievingNonExistingFruit_notOk() {
        RuntimeException noSuchFruitException =
                assertThrows(NoSuchFruitException.class,
                        () -> returnOperationHandler.handleOperation(
                                Constants.NO_SUCH_FRUIT_EXCEPTION_TRANSACTION));
        assertEquals(noSuchFruitException.getMessage(),
                EXPECTED_NO_SUCH_FRUIT_EXCEPTION_MESSAGE);
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
