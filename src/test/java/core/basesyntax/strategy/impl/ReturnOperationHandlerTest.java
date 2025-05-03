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

public class ReturnOperationHandlerTest {
    private static final FruitTransaction.Operation DEFAULT_OPERATION =
            FruitTransaction.Operation.RETURN;
    private static final FruitTransaction.Operation INCORRECT_OPERATION =
            FruitTransaction.Operation.BALANCE;
    private static final String DEFAULT_PRODUCT_NAME = "apple";
    private static final String EMPTY_PRODUCT_NAME = " ";
    private static final int DEFAULT_QUANTITY = 10;
    private static final int NEGATIVE_QUANTITY = -1;
    private static FruitTransaction defaultTransaction;
    private static OperationHandler operationHandler;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        operationHandler = new ReturnOperationHandler();
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
    public void handleOperation_handleNewProduct_ok() {
        FruitStorage.storage.clear();
        operationHandler.handleOperation(defaultTransaction);
        assertEquals("Test failed! Expected product '" + DEFAULT_PRODUCT_NAME
                        + "' with value '" + DEFAULT_QUANTITY + "', but actual value is '"
                        + FruitStorage.storage.get(DEFAULT_PRODUCT_NAME),
                (Integer) DEFAULT_QUANTITY, FruitStorage.storage.get(DEFAULT_PRODUCT_NAME));
    }

    @Test
    public void handleOperation_handleExistingProduct_ok() {
        operationHandler.handleOperation(new FruitTransaction(DEFAULT_OPERATION,
                DEFAULT_PRODUCT_NAME, DEFAULT_QUANTITY));
        assertEquals("Test failed! Expected product '" + DEFAULT_PRODUCT_NAME
                        + "' with value '" + (DEFAULT_QUANTITY << 1) + "', but actual value is '"
                        + FruitStorage.storage.get(DEFAULT_PRODUCT_NAME),
                (Integer) (DEFAULT_QUANTITY << 1), FruitStorage.storage.get(DEFAULT_PRODUCT_NAME));
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
