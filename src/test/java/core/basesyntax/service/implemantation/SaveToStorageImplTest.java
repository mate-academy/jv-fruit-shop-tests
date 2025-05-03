package core.basesyntax.service.implemantation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.SaveToStorageService;
import core.basesyntax.service.strategy.TransactionBalance;
import core.basesyntax.service.strategy.TransactionHandler;
import core.basesyntax.service.strategy.TransactionReturn;
import core.basesyntax.service.strategy.TransactionSell;
import core.basesyntax.service.strategy.TransactionStrategy;
import core.basesyntax.service.strategy.TransactionStrategyImpl;
import core.basesyntax.service.strategy.TransactionSupply;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class SaveToStorageImplTest {
    private SaveToStorageService saveToStorageService;
    private TransactionStrategy transactionStrategy;
    private Map<String, TransactionHandler> handlerMap;
    private List<String> stringList;

    @Test
    public void storeAll_correctData_ok() {
        handlerMap = new HashMap<>();
        handlerMap.put("b", new TransactionBalance());
        handlerMap.put("p", new TransactionSell());
        handlerMap.put("r", new TransactionReturn());
        handlerMap.put("s", new TransactionSupply());
        transactionStrategy = new TransactionStrategyImpl(handlerMap);
        saveToStorageService = new SaveToStorageImpl(transactionStrategy);
        stringList = new ArrayList<>();
        stringList.add("header");
        stringList.add("b,banana,17");
        stringList.add("p,banana,13");
        stringList.add("r,banana,4");
        stringList.add("s,banana,20");
        saveToStorageService.storeAll(stringList);
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("banana", 28);
        assertEquals(expectedMap, Storage.storage);
    }

    @Test(expected = RuntimeException.class)
    public void storeAll_inputListNull_notOk() {
        saveToStorageService.storeAll(null);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
