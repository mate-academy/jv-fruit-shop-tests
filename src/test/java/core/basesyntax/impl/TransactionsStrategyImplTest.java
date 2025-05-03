package core.basesyntax.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.TransactionsStrategy;
import core.basesyntax.strategy.impl.TransactionsStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionsStrategyImplTest {
    private static TransactionsStrategy transactionsStrategy;
    private static Map<String, FruitTransaction.Operation> operationMap;

    @BeforeClass
    public static void beforeClass() {
        operationMap = new HashMap<>();
        operationMap.put("b", FruitTransaction.Operation.BALANCE);
        operationMap.put("p", FruitTransaction.Operation.PURCHASE);
        operationMap.put("r", FruitTransaction.Operation.RETURN);
        operationMap.put("s", FruitTransaction.Operation.SUPPLY);
        transactionsStrategy = new TransactionsStrategyImpl(operationMap);
    }

    @Test(expected = RuntimeException.class)
    public void get_nullValue_notOk() {
        transactionsStrategy.get(null);
    }

    @Test(expected = RuntimeException.class)
    public void get_incorrectValue_notOk() {
        transactionsStrategy.get("t");
    }

    @Test
    public void get_correctValue_ok() {
        FruitTransaction.Operation actual = transactionsStrategy.get("b");
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        Assert.assertEquals(expected, actual);
    }
}
