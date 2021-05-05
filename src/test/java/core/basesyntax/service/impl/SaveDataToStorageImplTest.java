package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.FruitOperation;
import core.basesyntax.service.operations.OperationType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SaveDataToStorageImplTest {
    private static List<FruitRecordDto> test;
    private static Map<OperationType, FruitOperation> operationMap;
    private static SaveDataToStorageImpl saveDataToStorage;

    @BeforeClass
    public static void beforeClass() throws Exception {
        test = new ArrayList<>();
        operationMap = new HashMap<>();
        saveDataToStorage = new SaveDataToStorageImpl();
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
        test.clear();
        operationMap.clear();
    }

    @Test
    public void firstAndSecondParamsTest_Ok() {
        FruitRecordDto fruitRecordDto = new FruitRecordDto(OperationType.b,"banana",21);
        test.add(fruitRecordDto);
        FruitOperation addOperation = new AddOperation();
        operationMap.put(OperationType.b, addOperation);
        saveDataToStorage.saveData(test,operationMap);
        Assert.assertNotNull(test);
        Assert.assertNotNull(operationMap);
    }

    @Test
    public void firstAndSecondParamsIsEmpty_NotOk() {
        saveDataToStorage.saveData(test,operationMap);
        Assert.assertNotNull(test);
        Assert.assertNotNull(operationMap);
    }
}
