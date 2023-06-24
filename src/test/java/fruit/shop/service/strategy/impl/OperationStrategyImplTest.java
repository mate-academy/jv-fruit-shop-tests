package fruit.shop.service.strategy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import fruit.shop.model.FruitTransaction;
import fruit.shop.service.strategy.OperationHandler;
import fruit.shop.service.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy strategy;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> fruitMap = new HashMap<>();
        fruitMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        fruitMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        fruitMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        fruitMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        strategy = new OperationStrategyImpl(fruitMap);
    }

    @Test
    void getOperationHandler_nullInput_notOk() {
        assertThrows(RuntimeException.class,() -> strategy.getOperationHandler(null));
    }

    @Test
    void getOperationHandler_balance_ok() {
        assertEquals(BalanceHandler.class,
                strategy.getOperationHandler(FruitTransaction.Operation.BALANCE).getClass());
    }

    @Test
    void getOperationHandler_supply_ok() {
        assertEquals(SupplyHandler.class,
                strategy.getOperationHandler(FruitTransaction.Operation.SUPPLY).getClass());
    }

    @Test
    void getOperationHandler_return_ok() {
        assertEquals(ReturnHandler.class,
                strategy.getOperationHandler(FruitTransaction.Operation.RETURN).getClass());
    }

    @Test
    void getOperationHandler_purchase_ok() {
        assertEquals(PurchaseHandler.class,
                strategy.getOperationHandler(FruitTransaction.Operation.PURCHASE).getClass());
    }
}
