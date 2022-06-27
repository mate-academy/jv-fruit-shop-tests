package core.basesyntax.servise.transaction.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.servise.transaction.TransactionHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyTransactionHandlerTest {
    private static final int INIT_QUANTITY = 100;
    private static final int SUPPLY_QUANTITY = 50;
    private static final String ITEM = "item";

    private static TransactionHandler transactionHandler;

    @BeforeClass
    public static void init() {
        transactionHandler = new SupplyTransactionHandler();
    }

    @Before
    public void initStorage() {
        Storage.items.put(ITEM, INIT_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void proceed_notSuchItemInStorage_notOk() {
        transactionHandler.proceedTransaction("hello", Integer.MAX_VALUE);
    }

    @Test
    public void proceed_Ok() {
        transactionHandler.proceedTransaction(ITEM, SUPPLY_QUANTITY);
        int expected = INIT_QUANTITY + SUPPLY_QUANTITY;
        int actual = Storage.items.get(ITEM);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void clearStorage() {
        Storage.items.clear();
    }
}
