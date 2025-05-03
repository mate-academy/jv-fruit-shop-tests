package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.Filler;
import core.basesyntax.service.FruitOperationStrategy;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.FruitsOperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FillerImplTest {
    private static Map<FruitTransaction.Operation, FruitsOperationHandler> amountHandlerMap;
    private static FruitOperationStrategy fruitOperationStrategy;
    private static StorageDao storageDao;
    private static Filler filler;
    private static List<FruitTransaction> transactions;

    @BeforeClass
    public static void beforeAllTestMethods() {
        amountHandlerMap = new HashMap<>();
        storageDao = new StorageDaoImpl();
        amountHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(storageDao));
        amountHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(storageDao));
        amountHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(storageDao));
        amountHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(storageDao));
        fruitOperationStrategy = new FruitOperationStrategyImpl(amountHandlerMap);
        filler = new FillerImpl(fruitOperationStrategy);
        transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 50));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 70));
    }

    @Test
    public void fillStorage_size_ok() {
        filler.fillStorage(transactions);
        int expected = 1;
        int actual = Storage.report.size();

        assertEquals(expected,actual);
    }

    @Test
    public void fillStorage_quantity_ok() {
        filler.fillStorage(transactions);
        Integer actual = Storage.report.get("banana");
        Integer expected = 130;

        assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void fillStorage_quantity_notOk() {
        List<FruitTransaction> invalid = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 100),
                new FruitTransaction(Operation.PURCHASE, "banana", 200));
        filler.fillStorage(invalid);
    }

    @AfterClass
    public static void afterAllTestMethods() {
        Storage.report.clear();
    }
}
