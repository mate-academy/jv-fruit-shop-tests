package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitModel;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impl.BalanceOperationHandler;
import core.basesyntax.strategy.handler.impl.OperationStrategyImpl;
import core.basesyntax.strategy.handler.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.impl.ReturnOperationHandler;
import core.basesyntax.strategy.handler.impl.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final Integer BANANA_QUANTITY = 152;
    private static final Integer APPLE_QUANTITY = 90;
    private static FruitShopService fruitShopService;
    private static List<FruitModel> fruitModels;

    @BeforeClass
    public static void beforeAll() {
        Map<FruitModel.Operation, OperationHandler> operationHandlerMap = Map.of(
                FruitModel.Operation.BALANCE, new BalanceOperationHandler(),
                FruitModel.Operation.PURCHASE, new PurchaseOperationHandler(),
                FruitModel.Operation.SUPPLY, new SupplyOperationHandler(),
                FruitModel.Operation.RETURN, new ReturnOperationHandler()
        );
        fruitShopService = new FruitShopServiceImpl(new OperationStrategyImpl(operationHandlerMap));
        fruitModels = new ArrayList<>();
    }

    @Before
    public void init() {
        fruitModels = List.of(
                new FruitModel(FruitModel.Operation.BALANCE, BANANA, 20),
                new FruitModel(FruitModel.Operation.BALANCE, APPLE, 100),
                new FruitModel(FruitModel.Operation.SUPPLY, BANANA, 100),
                new FruitModel(FruitModel.Operation.PURCHASE, BANANA, 13),
                new FruitModel(FruitModel.Operation.RETURN, APPLE, 10),
                new FruitModel(FruitModel.Operation.PURCHASE, APPLE, 20),
                new FruitModel(FruitModel.Operation.PURCHASE, BANANA, 5),
                new FruitModel(FruitModel.Operation.SUPPLY, BANANA, 50))
                ;
    }

    @Test
    public void processData_validData_ok() {
        Map<String, Integer> expectedResult = new HashMap<>();
        expectedResult.put(BANANA, BANANA_QUANTITY);
        expectedResult.put(APPLE, APPLE_QUANTITY);
        fruitShopService.processData(fruitModels);
        assertEquals(expectedResult, Storage.fruitMap);
    }

    @Test(expected = RuntimeException.class)
    public void processDataNull_notOk() {
        fruitShopService.processData(null);
    }

    @Test(expected = RuntimeException.class)
    public void processData_invalidData_notOk() {
        fruitModels.add(new FruitModel(null, BANANA, 100));
        fruitShopService.processData(fruitModels);
    }
}
