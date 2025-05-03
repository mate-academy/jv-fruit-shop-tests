package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PurchaseOperationHandlerTest {
    private static final FruitTransaction.Operation DEFAULT_OPERATION =
            FruitTransaction.Operation.PURCHASE;
    private static final FruitTransaction.Operation INCORRECT_OPERATION =
            FruitTransaction.Operation.BALANCE;
    private static final String DEFAULT_PRODUCT_NAME = "apple";
    private static final String EMPTY_PRODUCT_NAME = " ";
    private static final int DEFAULT_QUANTITY = 10;
    private static final int SUBTRACT_QUANTITY_OK = 5;
    private static final int SUBTRACT_QUANTITY_NOT_OK = 11;
    private static final int NEGATIVE_QUANTITY = -1;
    private static FruitTransaction defaultTransaction;
    private static OperationHandler operationHandler;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Before
    public void init() {
        FruitStorage.storage.put(DEFAULT_PRODUCT_NAME, DEFAULT_QUANTITY);
        defaultTransaction = new FruitTransaction(
                DEFAULT_OPERATION, DEFAULT_PRODUCT_NAME, DEFAULT_QUANTITY);
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }

    @Test
    public void handleOperation_regularTransactionWithExistingProduct_ok() {
        operationHandler.handleOperation(new FruitTransaction(
                DEFAULT_OPERATION, DEFAULT_PRODUCT_NAME, SUBTRACT_QUANTITY_OK));
        assertEquals("Test failed! For product '" + DEFAULT_PRODUCT_NAME
                        + "' expected quantity: " + (DEFAULT_QUANTITY - SUBTRACT_QUANTITY_OK)
                        + ", but actual is: " + FruitStorage.storage.get(DEFAULT_PRODUCT_NAME),
                (Integer) (DEFAULT_QUANTITY - SUBTRACT_QUANTITY_OK),
                FruitStorage.storage.get(DEFAULT_PRODUCT_NAME));
    }

    @Test
    public void handleOperation_transactionValueBiggerThanAvailable_notOk() {
        exception.expect(RuntimeException.class);
        exception.reportMissingExceptionWithMessage(
                "Test failed! PURCHASE transaction with value '" + SUBTRACT_QUANTITY_NOT_OK
                        + "' is bigger than available in storage '" + DEFAULT_QUANTITY
                        + "'. Expected RuntimeException");
        operationHandler.handleOperation(new FruitTransaction(
                DEFAULT_OPERATION, DEFAULT_PRODUCT_NAME, SUBTRACT_QUANTITY_NOT_OK));
    }

    @Test
    public void handleOperation_transactionWithNegativeValue_notOk() {
        defaultTransaction.setQuantity(NEGATIVE_QUANTITY);
        exception.expect(RuntimeException.class);
        exception.reportMissingExceptionWithMessage(
                "Test failed! Transaction with negative value '" + NEGATIVE_QUANTITY
                        + "' is forbidden. Expected RuntimeException");
        operationHandler.handleOperation(defaultTransaction);
    }

    @Test
    public void handleOperation_emptyProductName_notOk() {
        FruitStorage.storage.put(EMPTY_PRODUCT_NAME, DEFAULT_QUANTITY);
        defaultTransaction.setProductName(EMPTY_PRODUCT_NAME);
        exception.expect(RuntimeException.class);
        exception.reportMissingExceptionWithMessage(
                "Test failed! Transaction with empty product name "
                        + "is forbidden. Expected RuntimeException");
        operationHandler.handleOperation(defaultTransaction);
    }

    @Test
    public void handleOperation_improperOperationType_notOk() {
        exception.expect(RuntimeException.class);
        exception.reportMissingExceptionWithMessage(
                "Test failed! Transaction with incorrect operation type '" + INCORRECT_OPERATION
                        + "'. Required '" + DEFAULT_OPERATION + "'. Expected RuntimeException");
        defaultTransaction.setOperation(INCORRECT_OPERATION);
        operationHandler.handleOperation(defaultTransaction);
    }
}
