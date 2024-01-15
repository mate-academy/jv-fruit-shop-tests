package core.basesyntax.services.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NegativeValueForOperationException;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.services.handlers.impl.SupplyOperationHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utilities.Constants;

public class SupplyOperationHandlerTest {
    private static final FruitTransaction ORANGE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, Constants.ORANGE, 10);
    private static final FruitTransaction APPLE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, Constants.APPLE, 10);
    private static final FruitTransaction BANANA_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, Constants.BANANA, 10);
    private static final FruitTransaction BANANA_FRUITTRANSACTION_NEGATIVE_QUANTITY =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, Constants.BANANA, -1);
    private static final int EXPECTED_RESULT_QUANTITY_OF_ORANGE = 110;
    private static final int EXPECTED_RESULT_QUANTITY_OF_APPLE = 110;
    private static final int EXPECTED_RESULT_QUANTITY_OF_BANANA = 110;
    private static final String EXPECTED_EXCEPTION_MESSAGE_NEGATIVE_NUMBER_SUPPLY =
            "Supply operation value for banana should've been positive but was "
                    + BANANA_FRUITTRANSACTION_NEGATIVE_QUANTITY.getQuantity();
    private static OperationHandler supplyOperationHandler;

    @BeforeAll
    static void initSupplyOperationHandlerAndStorage() {
        supplyOperationHandler = new SupplyOperationHandler();
        Storage.updateFruit(Constants.ORANGE, Constants.INITIAL_QUANTITY_OF_ORANGE);
        Storage.updateFruit(Constants.APPLE, Constants.INITIAL_QUANTITY_OF_APPLE);
        Storage.updateFruit(Constants.BANANA, Constants.INITIAL_QUANTITY_OF_BANANA);
    }

    @AfterAll
    static void clearStorage() {
        Storage.getFruits().clear();
    }

    @Test
    void handleOperation_supplyNegativeQuantity_notOk() {
        RuntimeException negativeValueForOperationException =
                assertThrows(NegativeValueForOperationException.class,
                        () -> supplyOperationHandler.handleOperation(
                                BANANA_FRUITTRANSACTION_NEGATIVE_QUANTITY));
        assertEquals(negativeValueForOperationException.getMessage(),
                EXPECTED_EXCEPTION_MESSAGE_NEGATIVE_NUMBER_SUPPLY);
    }

    @Test
    void handleOperation_goodTransactions_Ok() {
        supplyOperationHandler.handleOperation(ORANGE_FRUITTRANSACTION);
        supplyOperationHandler.handleOperation(APPLE_FRUITTRANSACTION);
        supplyOperationHandler.handleOperation(BANANA_FRUITTRANSACTION);
        assertEquals(EXPECTED_RESULT_QUANTITY_OF_ORANGE, Storage.getFruits().get(Constants.ORANGE));
        assertEquals(EXPECTED_RESULT_QUANTITY_OF_APPLE, Storage.getFruits().get(Constants.APPLE));
        assertEquals(EXPECTED_RESULT_QUANTITY_OF_BANANA, Storage.getFruits().get(Constants.BANANA));
    }
}
