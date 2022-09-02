package core.basesyntax.strategy.impl;

import core.basesyntax.dao.impl.FruitStorageDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.TransactionHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReturnTransactionHandlerTest {
    private TransactionHandler transactionHandler;

    @Before
    public void setUp() {
        transactionHandler = new ReturnTransactionHandler(new FruitStorageDaoImpl());
    }

    @Test
    public void balanceWithEmptyStorage_ok() {
        transactionHandler.apply(new FruitTransaction("r", "banana", 5));
        Assert.assertEquals("Expected value 5", 5, (int) FruitStorage.storage.get("banana"));
    }

    @Test
    public void balanceWithNotEmptyStorage_ok() {
        FruitStorage.storage.put("apple", 20);
        transactionHandler.apply(new FruitTransaction("r", "apple", 5));
        Assert.assertEquals("Expected value 25", 25, (int) FruitStorage.storage.get("apple"));
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }
}
