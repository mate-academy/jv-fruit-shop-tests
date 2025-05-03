package core.basesyntax.strategy.impl;

import core.basesyntax.dao.impl.FruitStorageDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.TransactionHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SupplyTransactionHandlerTest {
    private TransactionHandler transactionHandler;

    @Before
    public void setUp() {
        transactionHandler = new SupplyTransactionHandler(new FruitStorageDaoImpl());
    }

    @Test
    public void supplyWithEmptyStorage_ok() {
        transactionHandler.apply(new FruitTransaction("s", "banana", 5));
        Assert.assertEquals("Expected value 5", 5, (int) FruitStorage.storage.get("banana"));
    }

    @Test
    public void supplyWithNotEmptyStorage_ok() {
        FruitStorage.storage.put("apple", 20);
        transactionHandler.apply(new FruitTransaction("s", "apple", 5));
        Assert.assertEquals("Expected value 25", 25, (int) FruitStorage.storage.get("apple"));
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }
}
