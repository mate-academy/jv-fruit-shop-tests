package core.basesyntax.servise.impl;

import core.basesyntax.servise.TransactionStrategy;
import core.basesyntax.servise.transaction.impl.BalanceTransactionHandler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionStrategyImplTest {
    private static TransactionStrategy transactionStrategy;

    @BeforeClass
    public static void beforeClass() throws Exception {
        transactionStrategy = new TransactionStrategyImpl();
    }

    @Test
    public void get_balanceOperation_Ok() {
        Class<?> actual = transactionStrategy.get("b").getClass();
        Class<?> expected = BalanceTransactionHandler.class;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void get_nullOperation_notOk() {
        transactionStrategy.get(null).getClass();
    }
}
