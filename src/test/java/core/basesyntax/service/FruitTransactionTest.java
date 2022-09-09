package core.basesyntax.service;

import core.basesyntax.Operation;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.FruitTransactionImpl;
import core.basesyntax.strategy.FruitOperation;
import core.basesyntax.strategy.imp.BalanceFruitOperation;
import core.basesyntax.strategy.imp.PurchaseFruitOperation;
import core.basesyntax.strategy.imp.ReturnFruitOperation;
import core.basesyntax.strategy.imp.SupplyFruitOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionTest {
    private static FruitTransaction fruitTransaction;
    private static List<String[]> lines;

    @BeforeClass
    public static void setUp() {
        StorageDao storageDao = new StorageDaoImpl();
        Map<Operation, FruitOperation> transactionMap = new HashMap<>();
        lines = new ArrayList<>();
        transactionMap.put(Operation.BALANCE, new BalanceFruitOperation(storageDao));
        transactionMap.put(Operation.PURCHASE, new PurchaseFruitOperation(storageDao));
        transactionMap.put(Operation.RETURN, new ReturnFruitOperation(storageDao));
        transactionMap.put(Operation.SUPPLY, new SupplyFruitOperation(storageDao));
        fruitTransaction = new FruitTransactionImpl(transactionMap);
        lines.add(new String[]{"b", "banana", "20"});
        lines.add(new String[]{"b", "apple", "100"});
        lines.add(new String[]{"s", "banana", "100"});
        lines.add(new String[]{"r", "apple", "10"});
    }

    @Test
    public void apply_validDate_ok() {
        fruitTransaction.process(lines);
        int actualBanana = Storage.fruits.get("banana");
        int actualApple = Storage.fruits.get("apple");
        int expectedBanana = 120;
        int expectedApples = 110;
        Assert.assertEquals(actualBanana, expectedBanana);
        Assert.assertEquals(actualApple, expectedApples);
    }

    @Test(expected = RuntimeException.class)
    public void apply_nullDate_notOk() {
        fruitTransaction.process(null);
    }

    @After
    public void clear() {
        Storage.fruits.clear();
    }

}
