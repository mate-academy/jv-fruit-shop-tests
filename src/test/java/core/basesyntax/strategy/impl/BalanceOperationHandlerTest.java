package core.basesyntax.strategy.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assume.assumeThat;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BalanceOperationHandlerTest {
    private static final FruitTransaction.Operation DEFAULT_OPERATION =
            FruitTransaction.Operation.BALANCE;
    private static final FruitTransaction.Operation INCORRECT_OPERATION =
            FruitTransaction.Operation.PURCHASE;
    private static final String DEFAULT_PRODUCT_NAME = "apple";
    private static final String EMPTY_PRODUCT_NAME = " ";
    private static final int DEFAULT_QUANTITY = 10;
    private static final int UPDATED_QUANTITY = 50;
    private static final int NEGATIVE_QUANTITY = -1;
    private static FruitTransaction defaultTransaction;
    private static OperationHandler operationHandler;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        operationHandler = new BalanceOperationHandler();
    }

    @Before
    public void init() {
        defaultTransaction = new FruitTransaction(
                DEFAULT_OPERATION, DEFAULT_PRODUCT_NAME, DEFAULT_QUANTITY);
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }

    @Test
    public void handleOperation_addNewProduct_ok() {
        operationHandler.handleOperation(defaultTransaction);
        assumeThat("Test failed! For product name '" + DEFAULT_PRODUCT_NAME
                        + "' expected quantity '" + DEFAULT_QUANTITY + "', but actual is '"
                        + FruitStorage.storage.get(DEFAULT_PRODUCT_NAME) + '\'',
                FruitStorage.storage.get(DEFAULT_PRODUCT_NAME), is(DEFAULT_QUANTITY));
    }

    @Test
    public void handleOperation_updateExistingProduct_ok() {
        FruitStorage.storage.put(DEFAULT_PRODUCT_NAME, DEFAULT_QUANTITY);
        operationHandler.handleOperation(new FruitTransaction(
                DEFAULT_OPERATION, DEFAULT_PRODUCT_NAME, UPDATED_QUANTITY));
        assumeThat("Test failed! For product name '" + DEFAULT_PRODUCT_NAME
                        + "' expected quantity '" + UPDATED_QUANTITY + "', but actual is '"
                        + FruitStorage.storage.get(DEFAULT_PRODUCT_NAME) + '\'',
                FruitStorage.storage.get(DEFAULT_PRODUCT_NAME), is(UPDATED_QUANTITY));
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
        defaultTransaction.setProductName(EMPTY_PRODUCT_NAME);
        exception.expect(RuntimeException.class);
        exception.reportMissingExceptionWithMessage(
                "Test failed! Transaction with empty product name "
                        + "is forbidden. Expected RuntimeException");
        operationHandler.handleOperation(defaultTransaction);
    }

    @Test
    public void handleOperation_improperOperationType_notOk() {
        defaultTransaction.setOperation(INCORRECT_OPERATION);
        exception.expect(RuntimeException.class);
        exception.reportMissingExceptionWithMessage(
                "Test failed! Transaction with incorrect operation type " + INCORRECT_OPERATION
                        + "is forbidden. Expected RuntimeException");
        operationHandler.handleOperation(defaultTransaction);
    }
}
