package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Operation;
import core.basesyntax.operations.BalanceOperation;
import core.basesyntax.operations.OperationHandler;
import core.basesyntax.operations.PurchaseOperation;
import core.basesyntax.operations.ReturnOperation;
import core.basesyntax.operations.SupplyOperation;
import core.basesyntax.service.StrategyService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StrategyServiceImplTest {
    private static StrategyService strategyService;
    private static Map<Operation, OperationHandler> testMap;
    private static OperationHandler balanceHandler;
    private static OperationHandler returnHandler;
    private static OperationHandler purchaseHandler;
    private static OperationHandler supplyHandler;

    @BeforeAll
    static void setUp() {
        testMap = new HashMap<>();
        testMap.put(Operation.BALANCE, new BalanceOperation());
        testMap.put(Operation.PURCHASE, new PurchaseOperation());
        testMap.put(Operation.RETURN, new ReturnOperation());
        testMap.put(Operation.SUPPLY, new SupplyOperation());
        strategyService = new StrategyServiceImpl(testMap);
        balanceHandler = strategyService.getHandler(Operation.BALANCE);
        returnHandler = strategyService.getHandler(Operation.RETURN);
        purchaseHandler = strategyService.getHandler(Operation.PURCHASE);
        supplyHandler = strategyService.getHandler(Operation.SUPPLY);
    }

    @Test
    void getHandler_validHandlers_OK() {
        assertEquals(balanceHandler, testMap.get(Operation.BALANCE));
        assertEquals(returnHandler, testMap.get(Operation.RETURN));
        assertEquals(purchaseHandler, testMap.get(Operation.PURCHASE));
        assertEquals(supplyHandler, testMap.get(Operation.SUPPLY));
    }
}
