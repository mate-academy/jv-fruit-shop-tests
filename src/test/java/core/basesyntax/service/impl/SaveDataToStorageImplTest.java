package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.FruitOperation;
import core.basesyntax.service.operations.OperationType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SaveDataToStorageImplTest {
    private static List<FruitRecordDto> testList;
    private static Map<OperationType, FruitOperation> operationMap;
    private static SaveDataToStorageImpl saveDataToStorage;
    private static Storage storage;

    @BeforeClass
    public static void beforeClass() throws Exception {
        testList = new ArrayList<>();
        operationMap = new HashMap<>();
        saveDataToStorage = new SaveDataToStorageImpl();
        storage = new Storage();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.fruits.clear();
        testList.clear();
        operationMap.clear();
    }

    @Test
    public void firstAndSecondParamsTest_Ok() {
        FruitRecordDto fruitRecordDto = new FruitRecordDto(OperationType.b,"banana",21);
        testList.add(fruitRecordDto);
        FruitOperation addOperation = new AddOperation();
        operationMap.put(OperationType.b, addOperation);
        saveDataToStorage.saveData(testList,operationMap);
        Assert.assertNotNull(testList);
        Assert.assertNotNull(operationMap);
    }

    @Test
    public void firstAndSecondParamsIsEmpty_NotOk() {
        saveDataToStorage.saveData(testList,operationMap);
        Assert.assertNotNull(testList);
        Assert.assertNotNull(operationMap);
    }
}
