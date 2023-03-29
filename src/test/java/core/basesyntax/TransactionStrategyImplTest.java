package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.impl.transactions.BalanceHandlerImpl;
import core.basesyntax.service.impl.transactions.PurchaseHandlerImpl;
import core.basesyntax.service.impl.transactions.ReturnHandlerImpl;
import core.basesyntax.service.impl.transactions.SupplyHandlerImpl;
import core.basesyntax.strategy.TransactionStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TransactionStrategyImplTest {
    private static final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap =
            new HashMap<>();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void beforeAll() {
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandlerImpl());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandlerImpl());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandlerImpl());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandlerImpl());
    }

    @Test
    public void get_checkBalanceHandler_Ok() {
        FruitTransaction.Operation key = FruitTransaction.Operation.BALANCE;
        OperationHandler balanceHandler = new BalanceHandlerImpl();
        balanceHandler.getBalance(0, 20);
        int expected = 20;
        Assert.assertEquals(expected, new TransactionStrategyImpl(operationHandlerMap)
                .get(key).getBalance(0, 20));
    }

    @Test
    public void get_checkPurchaseHandler_Ok() {
        FruitTransaction.Operation key = FruitTransaction.Operation.PURCHASE;
        OperationHandler purchaseHandler = new PurchaseHandlerImpl();
        purchaseHandler.getBalance(50, 20);
        int expected = 30;
        Assert.assertEquals(expected, new TransactionStrategyImpl(operationHandlerMap)
                .get(key).getBalance(50, 20));
    }

    @Test
    public void get_purchaseHandlerThrowRuntimeException_NotOk() {
        FruitTransaction.Operation key = FruitTransaction.Operation.PURCHASE;
        OperationHandler purchaseHandler = new PurchaseHandlerImpl();
        int balance = 10 - 20;
        exception.expect(RuntimeException.class);
        exception.expectMessage("Quantity of purchase can't be bigger then balance in storage: "
                + "storage - "
                + balance + " purchase - "
                + 20);
        new TransactionStrategyImpl(operationHandlerMap).get(key).getBalance(10, 20);
    }

    @Test
    public void get_checkReturnHandler_Ok() {
        FruitTransaction.Operation key = FruitTransaction.Operation.RETURN;
        OperationHandler returnHandler = new ReturnHandlerImpl();
        returnHandler.getBalance(20, 10);
        int expected = 30;
        Assert.assertEquals(expected, new TransactionStrategyImpl(operationHandlerMap)
                .get(key).getBalance(20, 10));
    }

    @Test
    public void get_checkSupplyHandler_Ok() {
        FruitTransaction.Operation key = FruitTransaction.Operation.SUPPLY;
        OperationHandler supplyHandler = new SupplyHandlerImpl();
        supplyHandler.getBalance(30, 10);
        int expected = 40;
        Assert.assertEquals(expected, new TransactionStrategyImpl(operationHandlerMap)
                .get(key).getBalance(30, 10));
    }
}
