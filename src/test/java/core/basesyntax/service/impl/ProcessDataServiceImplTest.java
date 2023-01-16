package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ProcessDataService;
import core.basesyntax.strategy.operationhandler.OperationHandler;
import core.basesyntax.strategy.operationhandler.impl.BalanceOperationHandler;
import core.basesyntax.strategy.operationhandler.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.operationhandler.impl.ReturnOperationHandler;
import core.basesyntax.strategy.operationhandler.impl.SupplyOperationHandler;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProcessDataServiceImplTest {
    private static final int BANANA_RESULT_QUANTITY = 152;
    private static final int APPLE_RESULT_QUANTITY = 90;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static ProcessDataService processDataService;
    private static List<String> data;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeClass
    public static void beforeClass() {
        processDataService = new ProcessDataServiceImpl();
        data = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        operationHandlerMap = Map.of(
                        FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                        FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                        FruitTransaction.Operation.RETURN, new ReturnOperationHandler(),
                        FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
    }

    @Test
    public void processData_isOk() {
        processDataService.processData(data, operationHandlerMap);
        assertTrue(FruitStorage.fruitStorage.containsKey(BANANA));
        assertTrue(FruitStorage.fruitStorage.containsKey(APPLE));
        int actualBanana = FruitStorage.fruitStorage.get(BANANA);
        int actualApple = FruitStorage.fruitStorage.get(APPLE);
        assertEquals(BANANA_RESULT_QUANTITY, actualBanana);
        assertEquals(APPLE_RESULT_QUANTITY, actualApple);
    }
}
