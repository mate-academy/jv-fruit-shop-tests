package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Operation;
import core.basesyntax.operations.BalanceOperation;
import core.basesyntax.operations.OperationHandler;
import core.basesyntax.operations.PurchaseOperation;
import core.basesyntax.operations.ReturnOperation;
import core.basesyntax.operations.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StrategyServiceImplTest {
    private static Map<Operation, OperationHandler> testMap;

    @BeforeAll
    static void setUp() {
        testMap = new HashMap<>();
        testMap.put(Operation.BALANCE, new BalanceOperation());
        testMap.put(Operation.PURCHASE, new PurchaseOperation());
        testMap.put(Operation.RETURN, new ReturnOperation());
        testMap.put(Operation.SUPPLY, new SupplyOperation());
    }

    @Test
    void getHandler_validHandlers_OK() {
        assertEquals(BalanceOperation.class, testMap.get(Operation.BALANCE).getClass());
        assertEquals(ReturnOperation.class, testMap.get(Operation.RETURN).getClass());
        assertEquals(PurchaseOperation.class, testMap.get(Operation.PURCHASE).getClass());
        assertEquals(SupplyOperation.class, testMap.get(Operation.SUPPLY).getClass());
    }
}
