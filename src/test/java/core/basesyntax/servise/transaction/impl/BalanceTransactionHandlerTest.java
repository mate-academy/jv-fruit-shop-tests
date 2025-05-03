package core.basesyntax.servise.transaction.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.servise.transaction.TransactionHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceTransactionHandlerTest {
    private static final int BALANCE_QUANTITY = 100;
    private static final String ITEM = "item";
    private static TransactionHandler transactionHandler;

    @BeforeClass
    public static void init() {
        transactionHandler = new BalanceTransactionHandler();
    }

    @Test(expected = RuntimeException.class)
    public void proceed_duplicateBalanceForSameItem_notOk() {
        Storage.items.put(ITEM, BALANCE_QUANTITY);
        transactionHandler.proceedTransaction(ITEM, BALANCE_QUANTITY);
    }

    @Test
    public void proceed_Ok() {
        int expected = BALANCE_QUANTITY;
        transactionHandler.proceedTransaction(ITEM, BALANCE_QUANTITY);
        int actual = Storage.items.get(ITEM);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void clearStorage() {
        Storage.items.clear();
    }
}
