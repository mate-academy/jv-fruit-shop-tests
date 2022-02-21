package core.basesyntax.service;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.impl.BalanceOperationHandler;
import core.basesyntax.service.strategy.impl.OperationStrategyImpl;
import core.basesyntax.service.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.service.strategy.impl.ReturnOperationHandler;
import core.basesyntax.service.strategy.impl.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataProcessorTest {
    private static DataProcessor dataProcessor;
    private static List<String> parsedData;

    @BeforeClass
    public static void beforeClass() {
        HashMap<DataProcessorImpl.OperationType, OperationHandler> strategyMap = new HashMap<>();
        strategyMap.put(DataProcessorImpl.OperationType.BALANCE, new BalanceOperationHandler());
        strategyMap.put(DataProcessorImpl.OperationType.SUPPLY, new SupplyOperationHandler());
        strategyMap.put(DataProcessorImpl.OperationType.PURCHASE, new PurchaseOperationHandler());
        strategyMap.put(DataProcessorImpl.OperationType.RETURN, new ReturnOperationHandler());
        dataProcessor = new DataProcessorImpl(new OperationStrategyImpl(strategyMap));

        parsedData = new ArrayList<>();
        parsedData.add("b,orange,100");
        parsedData.add("p,orange,10");
        parsedData.add("r,orange,5");
        parsedData.add("s,banana,10");
    }

    @Test
    public void dataProcessor_registerTransaction_Ok() {
        Fruit expected = new Fruit("banana", 100);
        dataProcessor.registerTransaction("s,banana,100");
        Fruit actual = storage.get(0);
        //add
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAmount(), actual.getAmount());
        //substract
        expected.setAmount(90);
        dataProcessor.registerTransaction("p,banana,10");
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAmount(), actual.getAmount());
    }

    @Test
    public void dataProcessor_createFruits_Ok() {
        dataProcessor.createFruits(parsedData);
        Fruit expectedOrange = new Fruit("orange", 95);
        Fruit expectedBanana = new Fruit("banana", 10);
        Fruit actualOrange = storage.get(0);
        Fruit actualBanana = storage.get(1);
        assertEquals(expectedOrange.getName(), actualOrange.getName());
        assertEquals(expectedOrange.getAmount(), actualOrange.getAmount());
        assertEquals(expectedBanana.getName(), actualBanana.getName());
        assertEquals(expectedBanana.getAmount(), actualBanana.getAmount());
    }

    @After
    public void tearDown() {
        storage.clear();
    }
}
