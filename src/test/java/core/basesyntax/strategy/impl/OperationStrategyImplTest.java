package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        operationStrategy = new OperationStrategyImpl();
    }

    @Test
    public void getOperationHandler_balanceOperationTest_ok() {
        Class<BalanceOperationHandler> expected = BalanceOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.BALANCE).getClass();
        assertEquals(String.format("Should return -> %s%nfor operation -> %s%nbut was -> %s",
                        expected, FruitTransaction.Operation.BALANCE.name(), actual),
                expected, actual);
    }

    @Test
    public void getOperationHandler_supplyOperationTest_ok() {
        Class<SupplyOperationHandler> expected = SupplyOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.SUPPLY).getClass();
        assertEquals(String.format("Should return -> %s%nfor operation -> %s%nbut was -> %s",
                        expected, FruitTransaction.Operation.SUPPLY.name(), actual),
                expected, actual);
    }

    @Test
    public void getOperationHandler_returnOperationTest_ok() {
        Class<ReturnOperationHandler> expected = ReturnOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.RETURN).getClass();
        assertEquals(String.format("Should return -> %s%nfor operation -> %s%nbut was -> %s",
                        expected, FruitTransaction.Operation.RETURN.name(), actual),
                expected, actual);
    }

    @Test
    public void getOperationHandler_purchaseOperationTest_ok() {
        Class<PurchaseOperationHandler> expected = PurchaseOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.PURCHASE).getClass();
        assertEquals(String.format("Should return -> %s%nfor operation -> %s%nbut was -> %s",
                        expected, FruitTransaction.Operation.PURCHASE.name(), actual),
                expected, actual);
    }

    @After
    public void tearDown() {
        FruitStorage.fruits.clear();
    }
}
