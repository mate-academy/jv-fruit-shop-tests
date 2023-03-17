package core.basesyntax.strategy.impl;

import static core.basesyntax.model.Transaction.Operation.BALANCE;
import static core.basesyntax.model.Transaction.Operation.PURCHASE;
import static core.basesyntax.model.Transaction.Operation.RETURN;
import static core.basesyntax.model.Transaction.Operation.SUPPLY;

import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.TransactionStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TransactionStrategyImplTest extends Assert {
    public static final int AMOUNT_OF_OPERATIONS = 4;
    public static final String INVALID_OPERATION_CODE = "q";
    private static Map<Transaction.Operation, OperationHandler> operationsMap;
    private static TransactionStrategy strategy;
    private static OperationHandler supplyHandler;
    private static OperationHandler returnHandler;
    private static OperationHandler purchaseHandler;
    private static OperationHandler balanceHandler;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        operationsMap = new HashMap<>();
        supplyHandler = new SupplyOperationHandler();
        returnHandler = new ReturnOperationHandler();
        purchaseHandler = new PurchaseOperationHandler();
        balanceHandler = new BalanceOperationHandler();
    }

    @Before
    public void setUp() {
        operationsMap.put(SUPPLY, supplyHandler);
        operationsMap.put(RETURN, returnHandler);
        operationsMap.put(BALANCE, balanceHandler);
        operationsMap.put(PURCHASE, purchaseHandler);
    }

    @After
    public void tearDown() {
        operationsMap.clear();
    }

    @Test
    public void get_nonExistentOperation_notOk() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Code is invalid");
        strategy.get(Transaction.Operation.getByCode(INVALID_OPERATION_CODE));
    }

    @Test
    public void get_checkSupplyHandlerCLass_Ok() {
        strategy = new TransactionStrategyImpl(operationsMap);
        assertEquals(strategy.get(SUPPLY).getClass(), supplyHandler.getClass());
    }

    @Test
    public void get_checkBalanceHandlerClass_Ok() {
        strategy = new TransactionStrategyImpl(operationsMap);
        assertEquals(strategy.get(BALANCE).getClass(), balanceHandler.getClass());
    }

    @Test
    public void get_checkPurchaseHandlerClass_Ok() {
        strategy = new TransactionStrategyImpl(operationsMap);
        assertEquals(strategy.get(PURCHASE).getClass(), purchaseHandler.getClass());
    }

    @Test
    public void get_checkReturnHandlerClass_Ok() {
        strategy = new TransactionStrategyImpl(operationsMap);
        assertEquals(strategy.get(RETURN).getClass(), returnHandler.getClass());
    }

    @Test
    public void constructor_fourOperationsByDefault_Ok() {
        strategy = new TransactionStrategyImpl(operationsMap);
        assertEquals(AMOUNT_OF_OPERATIONS, operationsMap.size());
    }
}
