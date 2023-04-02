package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.enums.Operation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static final int APPLE_QUANTITY = 200;
    private static final int RETURN_OPERATION_VALUE = 100;
    private static FruitShopService fruitShopService;
    private static Map<String, OperationHandler> operationHandlersMap;
    private static OperationStrategy operationStrategy;
    private static List<FruitTransaction> fruitTransactionList;

    @BeforeClass
    public static void setUp() {
        FruitStorage.fruitsStorage.put("apple", APPLE_QUANTITY);

        operationHandlersMap = new HashMap<>();

        operationHandlersMap.put(Operation.BALANCE.getOperation(),
                new BalanceOperationHandler());
        operationHandlersMap.put(Operation.PURCHASE.getOperation(),
                new PurchaseOperationHandler());
        operationHandlersMap.put(Operation.RETURN.getOperation(),
                new ReturnOperationHandler());
        operationHandlersMap.put(Operation.SUPPLY.getOperation(),
                new SupplyOperationHandler());

        fruitTransactionList = new ArrayList<>();

        fruitTransactionList.add(new FruitTransaction(Operation.fromString("r"), "apple", 100));

        operationStrategy = new OperationStrategyImpl(operationHandlersMap);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);
    }

    @Test
    public void processOfOperations_checkIfCorrect_isOk() {
        Integer expected = APPLE_QUANTITY + RETURN_OPERATION_VALUE;
        fruitShopService.processOfOperations(fruitTransactionList);

        assertEquals(expected, FruitStorage.fruitsStorage.get("apple"));
    }
}
