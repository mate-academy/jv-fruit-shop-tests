package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static final FruitTransaction.Operation OPERATION_BALANCE =
            FruitTransaction.Operation.BALANCE;
    private static final FruitTransaction.Operation OPERATION_PURCHASE =
            FruitTransaction.Operation.PURCHASE;
    private static final FruitTransaction.Operation OPERATION_SUPPLY =
            FruitTransaction.Operation.SUPPLY;
    private static final FruitTransaction.Operation OPERATION_RETURN =
            FruitTransaction.Operation.RETURN;
    private static final Map<FruitTransaction.Operation, OperationHandler> map = new HashMap<>();
    private static OperationStrategy strategy;

    @BeforeAll
    static void beforeAll() {
        map.put(OPERATION_BALANCE, new BalanceOperation());
        map.put(OPERATION_PURCHASE, new PurchaseOperation());
        map.put(OPERATION_SUPPLY, new SupplyOperation());
        map.put(OPERATION_RETURN, new ReturnOperation());
        strategy = new OperationStrategyImpl(map);
    }

    @Test
    void getOperationHandler_balance_ok() {
        OperationHandler expectedBalance = new BalanceOperation();
        OperationHandler actualBalance = strategy.getOperationHandler(OPERATION_BALANCE);
        assertEquals(expectedBalance.getClass(), actualBalance.getClass());
    }

    @Test
    void getOperationHandler_purchase_ok() {
        OperationHandler expectedPurchase = new PurchaseOperation();
        OperationHandler actualPurchase = strategy.getOperationHandler(OPERATION_PURCHASE);
        assertEquals(expectedPurchase.getClass(), actualPurchase.getClass());
    }

    @Test
    void getOperationHandler_supply_ok() {
        OperationHandler expectedSupply = new SupplyOperation();
        OperationHandler actualSupply = strategy.getOperationHandler(OPERATION_SUPPLY);
        assertEquals(expectedSupply.getClass(), actualSupply.getClass());
    }

    @Test
    void getOperationHandler_return_ok() {
        OperationHandler expectedReturn = new ReturnOperation();
        OperationHandler actualReturn = strategy.getOperationHandler(OPERATION_RETURN);
        assertEquals(expectedReturn.getClass(), actualReturn.getClass());
    }
}
