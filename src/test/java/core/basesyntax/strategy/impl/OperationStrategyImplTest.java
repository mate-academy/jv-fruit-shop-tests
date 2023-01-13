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
    private static final FruitTransaction.Operation BALANCE = FruitTransaction.Operation.BALANCE;
    private static final FruitTransaction.Operation SUPPLY = FruitTransaction.Operation.SUPPLY;
    private static final FruitTransaction.Operation PURCHASE = FruitTransaction.Operation.PURCHASE;
    private static final FruitTransaction.Operation RETURN = FruitTransaction.Operation.RETURN;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        operationStrategy = new OperationStrategyImpl();
    }

    @Test
    public void getOperationHandler_balanceOperationTest() {
        Class<BalanceOperationHandler> expected = BalanceOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationHandler(BALANCE).getClass();
        assertEquals(String.format("Should return -> %s%n"
                                + "for operation -> %s%nbut was -> %s",
                        expected, BALANCE.name(), actual), expected, actual);
    }

    @Test
    public void getOperationHandler_supplyOperationTest() {
        Class<SupplyOperationHandler> expected = SupplyOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationHandler(SUPPLY).getClass();
        assertEquals(String.format("Should return -> %s%n"
                                + "for operation -> %s%nbut was -> %s",
                        expected, SUPPLY.name(), actual), expected, actual);
    }

    @Test
    public void getOperationHandler_returnOperationTest() {
        Class<ReturnOperationHandler> expected = ReturnOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationHandler(RETURN).getClass();
        assertEquals(String.format("Should return -> %s%n"
                                + "for operation -> %s%nbut was -> %s",
                        expected, RETURN.name(), actual), expected, actual);
    }

    @Test
    public void getOperationHandler_purchaseOperationTest() {
        Class<PurchaseOperationHandler> expected = PurchaseOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationHandler(PURCHASE).getClass();
        assertEquals(String.format("Should return -> %s%n"
                                + "for operation -> %s%nbut was -> %s",
                        expected, PURCHASE.name(), actual), expected, actual);
    }

    @After
    public void tearDown() {
        FruitStorage.fruits.clear();
    }
}
