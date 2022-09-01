package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitStorageService;
import core.basesyntax.strategy.FruitOperationHandler;
import core.basesyntax.strategy.impl.FruitBalanceOperationHandler;
import core.basesyntax.strategy.impl.FruitPurchaseOperationHandler;
import core.basesyntax.strategy.impl.FruitReturnOperationHandler;
import core.basesyntax.strategy.impl.FruitSupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FruitStorageServiceImplTest {
    private static FruitStorageService fruitStorageService;
    private static StorageDao storageDao;
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        Map<String, FruitOperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put("b", new FruitBalanceOperationHandler(storageDao));
        operationHandlers.put("s", new FruitSupplyOperationHandler(storageDao));
        operationHandlers.put("p", new FruitPurchaseOperationHandler(storageDao));
        operationHandlers.put("r", new FruitReturnOperationHandler(storageDao));
        fruitStorageService = new FruitStorageServiceImpl(operationHandlers);
    }

    @Test
    public void process_validData() {
        List<String> data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b,banana,100");
        data.add("s,banana,50");
        data.add("p,banana,90");
        data.add("r,banana,70");
        data.add("b,apple,120");
        data.add("s,apple,30");
        data.add("p,apple,80");
        data.add("r,apple,50");
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 120);
        expected.put("banana", 130);
        fruitStorageService.process(data);
        Map<String, Integer> actual = storageDao.getAll();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void process_nullDataInput_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Can't process data because input data is null or empty");
        fruitStorageService.process(null);
    }

    @Test
    public void process_emptyListInput_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Can't process data because input data is null or empty");
        fruitStorageService.process(new ArrayList<>());
    }

    @Test
    public void process_mapIsNull() {
        List<String> data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b,banana,100");
        data.add("b,apple,120");
        FruitStorageService fruitServiceForMapNull = new FruitStorageServiceImpl(null);
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Can`t call method 'process' if handlersMap is null");
        fruitServiceForMapNull.process(data);
    }

    @Test
    public void process_withoutReportHead_ok() {
        List<String> data = new ArrayList<>();
        data.add("b,banana,100");
        data.add("s,banana,50");
        data.add("p,banana,90");
        data.add("r,banana,70");
        data.add("b,apple,120");
        data.add("s,apple,30");
        data.add("p,apple,80");
        data.add("r,apple,50");
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 130);
        expected.put("apple", 120);
        fruitStorageService.process(data);
        Map<String, Integer> actual = storageDao.getAll();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void process_unValidReportHead() {
        List<String> data = new ArrayList<>();
        data.add("unValid Report Head");
        data.add("b,banana,100");
        data.add("b,apple,120");
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Input data is incorrect");
        fruitStorageService.process(data);
    }

    @Test
    public void process_unValidDataRows() {
        List<String> data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b ,banana -100");
        data.add("s,banana - 50");
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Input data is incorrect");
        fruitStorageService.process(data);
    }

    @After
    public void tearDown() {
        Storage.getStorage().clear();
    }
}
