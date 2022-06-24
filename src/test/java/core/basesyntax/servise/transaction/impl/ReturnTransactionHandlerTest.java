package core.basesyntax.servise.transaction.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.servise.transaction.TransactionHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnTransactionHandlerTest {
    private static final int INIT_QUANTITY = 100;
    private static final int RETURN_QUANTITY = 50;
    private static final String ITEM = "item";

    private static TransactionHandler transactionHandler;

    @BeforeClass
    public static void init() {
        transactionHandler = new ReturnTransactionHandler();
    }

    @Before
    public void initStorage() {
        Storage.items.put(ITEM, INIT_QUANTITY);
    }

    @Test
    public void proceed_Ok() {
        transactionHandler.proceedTransaction(ITEM, RETURN_QUANTITY);
        int expected = INIT_QUANTITY + RETURN_QUANTITY;
        int actual = Storage.items.get(ITEM);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void proceed_negativeQuantity_notOk() {
        transactionHandler.proceedTransaction(ITEM, -RETURN_QUANTITY);
    }

    @After
    public void clearStorage() {
        Storage.items.clear();
    }
}
