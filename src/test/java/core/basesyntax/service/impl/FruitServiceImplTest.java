package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static OperationStrategy operationStrategy;
    private static FruitTransaction balanceTransaction;
    private static FruitTransaction supplyTransaction;
    private static FruitTransaction returnTransaction;
    private static FruitTransaction purchaseTransaction;

    @BeforeClass
    public static void beforeAll() {
        StorageDao storageDao = new StorageDaoImpl();

        Map<FruitTransaction.Operation, OperationHandler> operations = new HashMap<>();
        operations.put(FruitTransaction.Operation.BALANCE, new BalanceOperationImpl(storageDao));
        operations.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationImpl(storageDao));
        operations.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationImpl(storageDao));
        operations.put(FruitTransaction.Operation.RETURN, new ReturnOperationImpl(storageDao));

        operationStrategy = new OperationStrategyImpl(operations);

        balanceTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                new Fruit("apple"),
                100);

        supplyTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                new Fruit("orange"),
                50);

        returnTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                new Fruit("banana"),
                30);

        purchaseTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                new Fruit("banana"),
                10);
    }

    @After
    public void afterEachTest() {
        Storage.getStorage().clear();
    }

    @Test
    public void processTransactions_processAllAvailableTransactions_Ok() {
        StorageDao storageDao = new StorageDaoImpl();
        OperationHandler balanceOperation = new BalanceOperationImpl(storageDao);
        balanceOperation.registerTransaction(balanceTransaction);
        OperationHandler supplyOperation = new SupplyOperationImpl(storageDao);
        supplyOperation.registerTransaction(supplyTransaction);
        OperationHandler returnOperation = new ReturnOperationImpl(storageDao);
        returnOperation.registerTransaction(returnTransaction);
        OperationHandler purchaseOperation = new PurchaseOperationImpl(storageDao);
        purchaseOperation.registerTransaction(purchaseTransaction);
        final Map<Fruit, Integer> expectedStorage = Storage.getStorage().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Storage.getStorage().clear();
        final FruitService fruitService = new FruitServiceImpl(operationStrategy);
        List<FruitTransaction> transactionList = new ArrayList<>();
        transactionList.add(balanceTransaction);
        transactionList.add(supplyTransaction);
        transactionList.add(returnTransaction);
        transactionList.add(purchaseTransaction);
        fruitService.processTransactions(transactionList);

        assertEquals(expectedStorage, Storage.getStorage());
    }
}
