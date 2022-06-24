package core.basesyntax.servise.transaction.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.servise.transaction.TransactionHandler;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseTransactionHandlerTest {
    private static final int INIT_QUANTITY = 100;
    private static final int PURCHASE_QUANTITY = 50;
    private static final String ITEM = "item";
    private static TransactionHandler transactionHandler;

    @BeforeClass
    public static void init() {
        transactionHandler = new PurchaseTransactionHandler();
    }

    @Before
    public void createStorage() {
        Storage.items.put(ITEM, INIT_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void proceed_negativeQuantity_notOk() {
        transactionHandler.proceedTransaction(ITEM, -PURCHASE_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void proceed_notEnoughQuantityStorage_notOk() {
        transactionHandler.proceedTransaction(ITEM, Integer.MAX_VALUE);
    }

    @Test
    public void proceed_Ok() {
        int expected = INIT_QUANTITY - PURCHASE_QUANTITY;
        transactionHandler.proceedTransaction(ITEM, PURCHASE_QUANTITY);
        int actual = Storage.items.get(ITEM);
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void clearStorage() {
        Storage.items.clear();
    }
}
