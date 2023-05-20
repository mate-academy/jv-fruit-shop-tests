package core.basesyntax.strategytest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.serviceimpl.operationhandlers.BalanceHandlerImpl;
import core.basesyntax.service.serviceimpl.operationhandlers.OperationHandler;
import core.basesyntax.service.serviceimpl.operationhandlers.PurchaseHandlerImpl;
import core.basesyntax.service.serviceimpl.operationhandlers.ReturnHandlerImpl;
import core.basesyntax.service.serviceimpl.operationhandlers.SupplyHandlerImpl;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.strategy.TransactionStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TransactionStrategyImplTest {
    public static final Map<FruitTransaction.Operation,
            OperationHandler> mapToHandle = new HashMap<>();
    private static OperationHandler expected;
    private static TransactionStrategy transactionStrategy;

    @BeforeAll
    static void beforeAll() {
        fillMapToHandle();
        transactionStrategy = new TransactionStrategyImpl(mapToHandle);
    }

    @Test
    void balanceOperationCheck_Ok() {
        expected = new BalanceHandlerImpl();
        OperationHandler actual =
                transactionStrategy.getOperationHandler(FruitTransaction.Operation.BALANCE);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void supplyOperationCheck_Ok() {
        expected = new SupplyHandlerImpl();
        OperationHandler actual =
                transactionStrategy.getOperationHandler(FruitTransaction.Operation.SUPPLY);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void purchaseOperationCheck_Ok() {
        expected = new PurchaseHandlerImpl();
        OperationHandler actual =
                transactionStrategy.getOperationHandler(FruitTransaction.Operation.PURCHASE);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void returnOperationCheck_Ok() {
        expected = new ReturnHandlerImpl();
        OperationHandler actual =
                transactionStrategy.getOperationHandler(FruitTransaction.Operation.RETURN);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void nullTransactionStrategy_NotOk() {
        TransactionStrategy transactionStrategy = new TransactionStrategyImpl(null);
        assertThrows(NullPointerException.class,
                () -> transactionStrategy.getOperationHandler(FruitTransaction.Operation.PURCHASE));
    }

    private static void fillMapToHandle() {
        mapToHandle.put(FruitTransaction.Operation.BALANCE, new BalanceHandlerImpl());
        mapToHandle.put(FruitTransaction.Operation.SUPPLY, new SupplyHandlerImpl());
        mapToHandle.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandlerImpl());
        mapToHandle.put(FruitTransaction.Operation.RETURN, new ReturnHandlerImpl());
    }
}
