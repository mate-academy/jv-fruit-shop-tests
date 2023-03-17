package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static final String BALANCE_CONST = "b";
    private static final String SUPPLY_CONST = "s";
    private static final String RETURN_CONST = "r";
    private static final String PURCHASE_CONST = "p";
    private static final String INCORRECT_DATA_INPU = "d";
    private static OperationStrategy operationStrategy;
    private static FruitTransaction.Operation operation;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> defaultMap = new HashMap();
        defaultMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        defaultMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        defaultMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        defaultMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        operationStrategy = new OperationStrategyImpl(defaultMap);
    }

    @Test
    void getBalanceHandler_ok() {
        OperationHandler expected = new BalanceOperationHandler();
        Assertions.assertEquals(operationStrategy
                        .get(operation.getByCode(BALANCE_CONST)).getClass(),
                expected.getClass());
    }

    @Test
    void getReturnHandler_ok() {
        OperationHandler expected = new ReturnOperationHandler();
        Assertions.assertEquals(operationStrategy
                        .get(operation.getByCode(RETURN_CONST)).getClass(),
                expected.getClass());
    }

    @Test
    void getPurchaseHandler_ok() {
        OperationHandler expected = new PurchaseOperationHandler();
        Assertions.assertEquals(operationStrategy
                        .get(operation.getByCode(PURCHASE_CONST)).getClass(),
                expected.getClass());
    }

    @Test
    void getSupplyHandler_ok() {
        OperationHandler expected = new SupplyOperationHandler();
        Assertions.assertEquals(operationStrategy
                        .get(operation.getByCode(SUPPLY_CONST)).getClass(),
                expected.getClass());
    }

    @Test
    void getHandler_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            operationStrategy.get(operation.getByCode(INCORRECT_DATA_INPU));
        });
    }
}
