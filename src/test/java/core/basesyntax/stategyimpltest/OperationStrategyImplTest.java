package core.basesyntax.stategyimpltest;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.BalanceOperationHandler;
import core.basesyntax.service.impl.PurchaseOperationHandler;
import core.basesyntax.service.impl.ReturnOperationHandler;
import core.basesyntax.service.impl.SupplyOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static final OperationHandler BALANCE_KEY_HANDLER = new BalanceOperationHandler();
    private static final OperationHandler SUPPLY_KEY_HANDLER = new SupplyOperationHandler();
    private static final OperationHandler PURCHASE_KEY_HANDLER = new PurchaseOperationHandler();
    private static final OperationHandler RETURN_KEY_HANDLER = new ReturnOperationHandler();

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, BALANCE_KEY_HANDLER);
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, SUPPLY_KEY_HANDLER);
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, PURCHASE_KEY_HANDLER);
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, RETURN_KEY_HANDLER);
    }

    @AfterAll
    static void afterAll() {
        operationHandlerMap.clear();
    }

    @Test
    void balance_validValue_Ok() {
        OperationHandler actual = operationHandlerMap.get(FruitTransaction.Operation.BALANCE);
        Assertions.assertEquals(BALANCE_KEY_HANDLER, actual);
    }

    @Test
    void supply_validValue_Ok() {
        OperationHandler actual = operationHandlerMap.get(FruitTransaction.Operation.SUPPLY);
        Assertions.assertEquals(SUPPLY_KEY_HANDLER, actual);
    }

    @Test
    void purchase_validValue_Ok() {
        OperationHandler actual = operationHandlerMap.get(FruitTransaction.Operation.PURCHASE);
        Assertions.assertEquals(PURCHASE_KEY_HANDLER, actual);
    }

    @Test
    void return_validValue_Ok() {
        OperationHandler actual = operationHandlerMap.get(FruitTransaction.Operation.RETURN);
        Assertions.assertEquals(RETURN_KEY_HANDLER, actual);
    }

    @Test
    void nullValue_Ok() {
        OperationHandler actual = operationHandlerMap.get(null);
        Assertions.assertNull(actual);
    }
}
