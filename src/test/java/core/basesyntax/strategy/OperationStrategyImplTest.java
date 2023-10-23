package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.ShopService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private ShopService shopService;
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> handlerMap;

    @BeforeEach
    void setUp() {
        handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation(shopService));
        handlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation(shopService));
        handlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation(shopService));
        handlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation(shopService));
        operationStrategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    void testGetOperationHandler_forReturn() {
        OperationHandler returnOperation = operationStrategy.get(FruitTransaction.Operation.RETURN);
        assertNotNull(returnOperation);
        assertTrue(returnOperation instanceof ReturnOperation);
    }

    @Test
    void testGetOperationHandler_forPurchase() {
        OperationHandler purchaseHandler = operationStrategy
                .get(FruitTransaction.Operation.PURCHASE);
        assertNotNull(purchaseHandler);
        assertTrue(purchaseHandler instanceof PurchaseOperation);
    }

    @Test
    void testGetOperationHandler_forSupply() {
        OperationHandler supplyHandler = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertNotNull(supplyHandler);
        assertTrue(supplyHandler instanceof SupplyOperation);
    }

    @Test
    void testGetOperationHandler_forBalance() {
        OperationHandler balanceHandler = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertNotNull(balanceHandler);
        assertTrue(balanceHandler instanceof BalanceOperation);
    }
}
