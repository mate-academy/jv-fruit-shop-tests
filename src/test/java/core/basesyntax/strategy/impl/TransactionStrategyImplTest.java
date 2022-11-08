package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.GeneralOperation;
import core.basesyntax.strategy.TransactionStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionStrategyImplTest {
    private static TransactionStrategy transactionStrategy;

    @BeforeClass
    public static void setUp() {
        Map<FruitTransaction.Operation, GeneralOperation> operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlersMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationHandlersMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlersMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        transactionStrategy = new TransactionStrategyImpl(operationHandlersMap);
    }

    @Test
    public void get_BalanceHandler_isOk() {
        GeneralOperation expected = new BalanceHandler();
        GeneralOperation actual = transactionStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void get_PurchaseHandler_isOk() {
        GeneralOperation expected = new PurchaseHandler();
        GeneralOperation actual = transactionStrategy.get(FruitTransaction.Operation.PURCHASE);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void get_ReturnHandler_isOk() {
        GeneralOperation expected = new ReturnHandler();
        GeneralOperation actual = transactionStrategy.get(FruitTransaction.Operation.RETURN);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void get_SupplyHandler_isOk() {
        GeneralOperation expected = new SupplyHandler();
        GeneralOperation actual = transactionStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertEquals(expected.getClass(), actual.getClass());
    }
}
