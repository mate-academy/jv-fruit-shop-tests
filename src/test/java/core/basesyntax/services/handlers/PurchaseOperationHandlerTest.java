package core.basesyntax.services.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NegativeResultException;
import core.basesyntax.exceptions.NegativeValueForOperationException;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.services.handlers.impl.PurchaseOperationHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private static final String ORANGE = "orange";
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int INITIAL_QUANTITY_OF_ORANGE = 100;
    private static final int INITIAL_QUANTITY_OF_APPLE = 100;
    private static final int INITIAL_QUANTITY_OF_BANANA = 100;
    private static final int EXPECTED_RESULT_QUANTITY_OF_ORANGE = 90;
    private static final int EXPECTED_RESULT_QUANTITY_OF_APPLE = 90;
    private static final int EXPECTED_RESULT_QUANTITY_OF_BANANA = 90;

    private static final FruitTransaction ORANGE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, ORANGE, 10);
    private static final FruitTransaction APPLE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, 10);
    private static final FruitTransaction BANANA_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 10);
    private static final FruitTransaction PURCHASE_NEGATIVE_QUANTITY =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, -1);
    private static final FruitTransaction PURCHASE_WITH_NEGATIVE_RESULT =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 1000);
    private static final String EXPECTED_EXCEPTION_MESSAGE_NEGATIVE_NUMBER_PURCHASE =
            "Purchase operation value for banana should've been positive but was " +
                    PURCHASE_NEGATIVE_QUANTITY.getQuantity();
    private static final String EXPECTED_EXCEPTION_MESSAGE_BUY_MORE_THAN_HAVE =
            "Insufficient stock for purchase: Requested " +
                    PURCHASE_WITH_NEGATIVE_RESULT.getQuantity() + " but only " +
                    INITIAL_QUANTITY_OF_BANANA + " available for " +
                    PURCHASE_WITH_NEGATIVE_RESULT.getFruit();
    private static OperationHandler purchaseOperationHandler;

    @BeforeAll
    static void initPurchaseOperationHandlerAndStorage() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        Storage.updateFruit(ORANGE, INITIAL_QUANTITY_OF_ORANGE);
        Storage.updateFruit(APPLE, INITIAL_QUANTITY_OF_APPLE);
        Storage.updateFruit(BANANA, INITIAL_QUANTITY_OF_BANANA);
    }

    @AfterAll
    static void closePurchaseOperationHandler() {
        purchaseOperationHandler = null;
    }

    @Test
    void handleOperation_purchaseNegativeQuantity_notOk() {
        RuntimeException negativeValueForOperationException =
                assertThrows(NegativeValueForOperationException.class,
                        () -> purchaseOperationHandler.handleOperation(PURCHASE_NEGATIVE_QUANTITY));
        assertEquals(negativeValueForOperationException.getMessage(),
                EXPECTED_EXCEPTION_MESSAGE_NEGATIVE_NUMBER_PURCHASE);
    }

    @Test
    void handleOperation_purchaseMoreThanHave_notOk() {
        RuntimeException negativeResultException = assertThrows(NegativeResultException.class,
                () -> purchaseOperationHandler.handleOperation(PURCHASE_WITH_NEGATIVE_RESULT));
        assertEquals(negativeResultException.getMessage(),
                EXPECTED_EXCEPTION_MESSAGE_BUY_MORE_THAN_HAVE);
    }

    @Test
    void handleOperation_goodTransactions_Ok() {
        purchaseOperationHandler.handleOperation(ORANGE_FRUITTRANSACTION);
        purchaseOperationHandler.handleOperation(APPLE_FRUITTRANSACTION);
        purchaseOperationHandler.handleOperation(BANANA_FRUITTRANSACTION);
        assertEquals(EXPECTED_RESULT_QUANTITY_OF_ORANGE, Storage.getFruits().get(ORANGE));
        assertEquals(EXPECTED_RESULT_QUANTITY_OF_APPLE, Storage.getFruits().get(APPLE));
        assertEquals(EXPECTED_RESULT_QUANTITY_OF_BANANA, Storage.getFruits().get(BANANA));
    }
}
