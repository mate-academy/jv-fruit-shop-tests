package core.basesyntax.service;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.DataProcessorImpl;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.impl.MinusOperationHandler;
import core.basesyntax.service.strategy.impl.OperationStrategyImpl;
import core.basesyntax.service.strategy.impl.PlusOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataProcessorTest {
    private static DataProcessor dataProcessor;
    private static List<String> parsedData;

    @BeforeClass
    public static void beforeClass() {
        HashMap<DataProcessorImpl.OperationType, OperationHandler> strategyMap = new HashMap<>();
        strategyMap.put(DataProcessorImpl.OperationType.BALANCE, new PlusOperationHandler());
        strategyMap.put(DataProcessorImpl.OperationType.SUPPLY, new PlusOperationHandler());
        strategyMap.put(DataProcessorImpl.OperationType.PURCHASE, new MinusOperationHandler());
        strategyMap.put(DataProcessorImpl.OperationType.RETURN, new PlusOperationHandler());
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
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAmount(), actual.getAmount());
        expected.setAmount(90);
        dataProcessor.registerTransaction("p,banana,10");
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAmount(), actual.getAmount());
    }

    @Test(expected = RuntimeException.class)
    public void dataProcessor_registerTransaction_invalidAmount_notOk() {
        dataProcessor.registerTransaction("s,banana,100");
        dataProcessor.registerTransaction("p,banana,101");
    }

    @Test(expected = NoSuchElementException.class)
    public void dataProcessor_registerTransaction_noSuchElement_notOk() {
        dataProcessor.registerTransaction("p,banana,100");
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
