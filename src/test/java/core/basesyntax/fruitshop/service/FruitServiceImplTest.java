package core.basesyntax.fruitshop.service;

import core.basesyntax.fruitshop.dao.FruitStorageDao;
import core.basesyntax.fruitshop.dao.FruitStorageDaoImpl;
import core.basesyntax.fruitshop.service.shopoperation.OperationHandler;
import core.basesyntax.fruitshop.service.shopoperation.OperationType;
import core.basesyntax.fruitshop.service.shopoperation.StorageDecreaseHandler;
import core.basesyntax.fruitshop.service.shopoperation.StorageIncreaseHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static FruitService fruitService;

    @BeforeClass
    public static void beforeClass() {
        FruitStorageDao fruitStorageDao = new FruitStorageDaoImpl();
        OperationHandler storageIncreaseHandler = new StorageIncreaseHandler(
                fruitStorageDao);
        Map<OperationType, OperationHandler> operationMap = new HashMap<>();
        operationMap.put(OperationType.BALANCE, storageIncreaseHandler);
        operationMap.put(OperationType.SUPPLY, storageIncreaseHandler);
        operationMap.put(OperationType.RETURN, storageIncreaseHandler);
        operationMap.put(OperationType.PURCHASE, new StorageDecreaseHandler(
                fruitStorageDao));
        fruitService = new FruitServiceImpl(operationMap);
    }

    @Test
    public void getOperation_ByOperationTypeBalance_isOk() {
        OperationHandler actual = fruitService.getOperation(OperationType.BALANCE);
        Assert.assertTrue(actual instanceof StorageIncreaseHandler);
    }

    @Test
    public void getOperation_ByOperationTypePurchase_isOk() {
        OperationHandler actual = fruitService.getOperation(OperationType.PURCHASE);
        Assert.assertTrue(actual instanceof StorageDecreaseHandler);
    }

    @Test
    public void getOperation_ByOperationTypeNull() {
        OperationHandler actual = fruitService.getOperation(null);
        Assert.assertNull(actual);
    }
}
