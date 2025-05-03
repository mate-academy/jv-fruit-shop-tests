package core.basesyntax.fruitshop.service;

import core.basesyntax.fruitshop.fruitstoragedb.FruitStorage;
import core.basesyntax.fruitshop.model.Fruit;
import core.basesyntax.fruitshop.model.OperationType;
import core.basesyntax.fruitshop.model.RecordDto;
import core.basesyntax.fruitshop.service.operations.BalanceOperationHandler;
import core.basesyntax.fruitshop.service.operations.OperationHandler;
import core.basesyntax.fruitshop.service.operations.PurchaseOperationHandler;
import core.basesyntax.fruitshop.service.operations.ReturnOperationHandler;
import core.basesyntax.fruitshop.service.operations.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private List<RecordDto> recordsList;
    private FruitShopServiceImpl fruitShopService;
    private Map<OperationType, OperationHandler> operationHandlerMap;
    private Map<Fruit, Integer> initialStorage;

    @Before
    public void setUp() throws Exception {
        recordsList = new ArrayList<>();
        RecordDto firstDto = new RecordDto(OperationType.BALANCE, new Fruit("banana"),10);
        RecordDto secondDto = new RecordDto(OperationType.BALANCE, new Fruit("apple"),0);
        RecordDto thirdDto = new RecordDto(OperationType.SUPPLY, new Fruit("banana"), 100);
        RecordDto fourthDto = new RecordDto(OperationType.PURCHASE, new Fruit("banana"),13);
        RecordDto fifthDto = new RecordDto(OperationType.RETURN, new Fruit("pineapple"),10);
        recordsList.add(firstDto);
        recordsList.add(secondDto);
        recordsList.add(thirdDto);
        recordsList.add(fourthDto);
        recordsList.add(fifthDto);
        initialStorage = FruitStorage.getStorage();
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(OperationType.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(OperationType.RETURN, new ReturnOperationHandler());
        operationHandlerMap.put(OperationType.SUPPLY, new SupplyOperationHandler());
    }

    @Test
    public void changed_fruitStorageModifier_Ok() {
        fruitShopService = new FruitShopServiceImpl(new OperationStrategyImpl(operationHandlerMap));
        fruitShopService.fruitStorageModifier(recordsList);
        Map<Fruit, Integer> expectedResult = new HashMap<>();
        expectedResult.put(new Fruit("banana"),97);
        expectedResult.put(new Fruit("apple"),0);
        expectedResult.put(new Fruit("pineapple"),10);
        Map<Fruit, Integer> changedStorage = initialStorage;
        Assert.assertEquals(expectedResult, changedStorage);
    }

    @After
    public void afterEachTest() throws Exception {
        FruitStorage.getStorage().clear();
    }
}
