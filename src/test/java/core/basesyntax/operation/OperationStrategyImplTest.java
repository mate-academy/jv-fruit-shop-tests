package core.basesyntax.operation;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static FruitTransactionDao fruitTransactionDao;
    private static Map<FruitTransaction.Operation,OperationHandler> operationHandlerMap;
    private static final String VALID_FIRST_OPERATION = "b";
    private static final String VALID_SECOND_OPERATION = "p";
    private static final String VALID_THIRD_OPERATION = "r";
    private static final String VALID_FOURTH_OPERATION = "s";
    private static final String INVALID_FIRST_OPERATION = "m";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        operationHandlerMap = new HashMap<>();
    }

    @Before
    public void beforeEach() {
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(fruitTransactionDao));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void operationStrategy_passValidSymbol_Ok() {
        OperationHandler first = operationStrategy.getOperationHandler(VALID_FIRST_OPERATION);
        Assert.assertTrue("Operation handler was not added correctly.",
                operationHandlerMap.containsValue(first));
        OperationHandler second = operationStrategy.getOperationHandler(VALID_SECOND_OPERATION);
        Assert.assertTrue("Operation handler was not added correctly.",
                operationHandlerMap.containsValue(second));
        OperationHandler third = operationStrategy.getOperationHandler(VALID_THIRD_OPERATION);
        Assert.assertTrue("Operation handler was not added correctly.",
                operationHandlerMap.containsValue(third));
        OperationHandler fourth = operationStrategy.getOperationHandler(VALID_FOURTH_OPERATION);
        Assert.assertTrue("Operation handler was not added correctly.",
                operationHandlerMap.containsValue(fourth));
    }

    @Test
    public void operationStrategy_passInvalidSymbol_NotOk() {
        thrown.expect(RuntimeException.class);
        operationStrategy.getOperationHandler(INVALID_FIRST_OPERATION);
    }

    @Test
    public void operationStrategy_nullValue_not0k() {
        thrown.expect(RuntimeException.class);
        operationStrategy.getOperationHandler(null);
    }

    @After
    public void afterEach() {
        operationHandlerMap.clear();
    }
}
