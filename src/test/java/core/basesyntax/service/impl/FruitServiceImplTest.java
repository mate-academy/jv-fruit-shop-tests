package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationWithFruit;
import core.basesyntax.model.TransactionInfo;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.OperationService;
import core.basesyntax.strategy.Operation;
import core.basesyntax.strategy.impl.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.impl.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandlerImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static List<TransactionInfo> transactionInfoList;
    private static FruitService fruitService;

    @BeforeClass
    public static void beforeClass() {
        Map<OperationWithFruit, Operation> openFilesHandlerMap = new HashMap<>();
        openFilesHandlerMap.put(OperationWithFruit.BALANCE, new BalanceOperationHandlerImpl());
        openFilesHandlerMap.put(OperationWithFruit.PURCHASE, new PurchaseOperationHandlerImpl());
        openFilesHandlerMap.put(OperationWithFruit.SUPPLY, new SupplyOperationHandlerImpl());
        openFilesHandlerMap.put(OperationWithFruit.RETURN, new ReturnOperationHandlerImpl());
        OperationService operationService = new OperationServiceImpl(openFilesHandlerMap);
        fruitService = new FruitServiceImpl(operationService);

        transactionInfoList = new ArrayList<>();
        transactionInfoList.add(new TransactionInfo("b", new Fruit("banana"), 20));
        transactionInfoList.add(new TransactionInfo("b", new Fruit("apple"), 100));
        transactionInfoList.add(new TransactionInfo("s", new Fruit("banana"), 100));
        transactionInfoList.add(new TransactionInfo("p", new Fruit("banana"), 13));
        transactionInfoList.add(new TransactionInfo("r", new Fruit("apple"), 10));
        transactionInfoList.add(new TransactionInfo("p", new Fruit("apple"), 20));
        transactionInfoList.add(new TransactionInfo("p", new Fruit("banana"), 5));
        transactionInfoList.add(new TransactionInfo("s", new Fruit("banana"), 50));
    }

    @Test
    public void process_currentAmount_ok() {
        int expected = 90;
        fruitService.process(transactionInfoList);
        int actual = Storage.storage.get(new Fruit("apple"));
        Assert.assertEquals(expected, actual);

        expected = 152;
        fruitService.process(transactionInfoList);
        actual = Storage.storage.get(new Fruit("banana"));
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
