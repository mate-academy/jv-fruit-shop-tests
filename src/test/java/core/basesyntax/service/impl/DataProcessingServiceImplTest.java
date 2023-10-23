package core.basesyntax.service.impl;

import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataProcessingService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataProcessingServiceImplTest {
    private static List<String> data;
    private static Fruit bananaInStorageAfterProcessing;
    private static Fruit appleInStorageAfterProcessing;
    private static DataProcessingService dataProcessingService;
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> handlers;

    @BeforeClass
    public static void beforeClass() throws Exception {
        handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(handlers);
        dataProcessingService = new DataProcessingServiceImpl(operationStrategy);
        bananaInStorageAfterProcessing = new Fruit("banana", 152);
        appleInStorageAfterProcessing = new Fruit("apple", 90);
        data = new ArrayList<>();
        data.add("b,banana,20");
        data.add("b,apple,100");
        data.add("s,banana,100");
        data.add("p,banana,13");
        data.add("r,apple,10");
        data.add("p,apple, 20");
        data.add("p,banana,5");
        data.add("s,banana,50");
    }

    @Test
    public void processTransaction_ok() {
        dataProcessingService.processTransaction(data);
        Assert.assertTrue(Storage.fruits.contains(bananaInStorageAfterProcessing));
        Assert.assertTrue(Storage.fruits.contains(appleInStorageAfterProcessing));
    }

    @Test(expected = RuntimeException.class)
    public void processTransaction_NullData_notOk() {
        dataProcessingService.processTransaction(null);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
