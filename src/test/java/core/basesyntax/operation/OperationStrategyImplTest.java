package core.basesyntax.operation;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static FruitTransactionDao fruitTransactionDao;
    private static Map<FruitTransaction.Operation,OperationHandler> operationHandlerMap;
    private static final String BALANCE_OPERATION = "b";
    private static final String PURCHASE_OPERATION = "p";
    private static final String RETURN_OPERATION = "r";
    private static final String SUPPLY_OPERATION = "s";
    private static final String INVALID_OPERATION = "m";

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
    public void getOperationHandler_passValidSymbol_Ok() {
        OperationHandler first = operationStrategy.getOperationHandler(BALANCE_OPERATION);
        Assert.assertTrue("Balance operation handler was not added",
                operationHandlerMap.containsValue(first));
        OperationHandler second = operationStrategy.getOperationHandler(PURCHASE_OPERATION);
        Assert.assertTrue("Purchase operation handler was not added",
                operationHandlerMap.containsValue(second));
        OperationHandler third = operationStrategy.getOperationHandler(RETURN_OPERATION);
        Assert.assertTrue("Return operation handler was not added",
                operationHandlerMap.containsValue(third));
        OperationHandler fourth = operationStrategy.getOperationHandler(SUPPLY_OPERATION);
        Assert.assertTrue("Supply operation handler was not added",
                operationHandlerMap.containsValue(fourth));
    }

    @Test(expected = RuntimeException.class)
    public void getOperationHandler_passInvalidSymbol_NotOk() {
        operationStrategy.getOperationHandler(INVALID_OPERATION);
    }

    @Test(expected = RuntimeException.class)
    public void getOperationHandler_nullValue_not0k() {
        operationStrategy.getOperationHandler(null);
    }

    @After
    public void afterEach() {
        operationHandlerMap.clear();
        Storage.fruitTransactionStorage.clear();
    }
}
