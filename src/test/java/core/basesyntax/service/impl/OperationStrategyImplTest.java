package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.impl.handlers.BalanceOperationHandlerImpl;
import core.basesyntax.service.impl.handlers.PurchaseOperationHandlerImpl;
import core.basesyntax.service.impl.handlers.ReturnOperationHandlerImpl;
import core.basesyntax.service.impl.handlers.SupplyOperationHandlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static final FruitTransaction.Operation TEST_OPERATION = BALANCE;
    private static Map<FruitTransaction.Operation, OperationHandler> strategies;
    private OperationStrategy operationStrategy;
    private OperationHandler actual;

    @BeforeClass
    public static void oneTimeSetUp() {
        strategies = new HashMap<>();
        strategies.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandlerImpl());
        strategies.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandlerImpl());
        strategies.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandlerImpl());
        strategies.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandlerImpl());
    }

    @AfterClass
    public static void oneTimeTearDown() {
        strategies = null;
    }

    @Before
    public void setUp() {
        operationStrategy = new OperationStrategyImpl(strategies);
    }

    @Test
    public void getHandler_Work_Ok() {
        actual = new BalanceOperationHandlerImpl();
        OperationHandler expected = operationStrategy.getHandler(TEST_OPERATION);
        assertEquals(expected.getClass(), actual.getClass());
    }
}
