package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static OperationStrategy strategy;

    @BeforeAll
    static void beforeAll() {
        strategy = new OperationStrategyImpl();
    }

    @Test
    public void getOperationService_balanceOperation_ok() {
        Class<BalanceOperationHandler> expected = BalanceOperationHandler.class;
        Class<? extends OperationHandler> actual =
                strategy.getOperationService(FruitTransaction.Operation.BALANCE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationService_supplyOperation_ok() {
        Class<SupplyOperationHandler> expected = SupplyOperationHandler.class;
        Class<? extends OperationHandler> actual =
                strategy.getOperationService(FruitTransaction.Operation.SUPPLY).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationService_returnOperation_ok() {
        Class<ReturnOperationHandler> expected = ReturnOperationHandler.class;
        Class<? extends OperationHandler> actual =
                strategy.getOperationService(FruitTransaction.Operation.RETURN).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationService_purchaseOperation_ok() {
        Class<PurchaseOperationHandler> expected = PurchaseOperationHandler.class;
        Class<? extends OperationHandler> actual =
                strategy.getOperationService(FruitTransaction.Operation.PURCHASE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationService_NonExistentOperation_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> strategy.getOperationService(FruitTransaction.Operation.valueOf(
                        "nonExistentOperation")));
    }
}
