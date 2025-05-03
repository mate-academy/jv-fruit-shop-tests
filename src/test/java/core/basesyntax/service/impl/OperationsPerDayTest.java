package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.StorageService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class OperationsPerDayTest {
    private StorageService storageServiceAdd = new StorageServiceAddImpl(new FruitDaoImpl());

    @After
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void checkOperations() {
        Map<String, StorageService> operationStorageMap = new HashMap<>();
        operationStorageMap.put("b",storageServiceAdd);
        OperationsPerDay operations = new OperationsPerDay();
        List list = new ArrayList(){};
        list.add("b,checkingFruitEmpty,100");
        list.add("b,checkingFruitRealZero,100");
        operations.createOperations(list,operationStorageMap);
        assertEquals("checkingFruitRealZero", Storage.fruits.get(0).getNameFruit());
    }
}
