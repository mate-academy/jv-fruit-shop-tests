package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Filler;
import core.basesyntax.service.FruitOperationStrategy;
import core.basesyntax.service.LineGenerator;
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

public class LineGeneratorImplTest {
    private static Map<FruitTransaction.Operation, FruitsOperationHandler> amountHandlerMap;
    private static FruitOperationStrategy fruitOperationStrategy;
    private static StorageDao storageDao;
    private static Filler filler;
    private static List<FruitTransaction> transactions;
    private static LineGenerator lineGenerator;

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
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "strawberry", 100));
        filler.fillStorage(transactions);
        lineGenerator = new LineGeneratorImpl();
    }

    @Test
    public void createReport_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,130" + System.lineSeparator() + "strawberry,100";
        String actual = lineGenerator.createReport(storageDao.getAll());
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_empty_ok() {
        Map<String, Integer> empty = new HashMap<>();
        String expected = "fruit,quantity";
        String actual = lineGenerator.createReport(empty);
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterAllTestMethods() {
        Storage.report.clear();
    }
}
