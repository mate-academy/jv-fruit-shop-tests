package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import strategy.impl.BalanceTransactionImpl;
import strategy.impl.OperationStrategyImpl;
import strategy.impl.PurchaseTransactionImpl;
import strategy.impl.ReturnTransactionImpl;
import strategy.impl.SupplyTransactionImpl;

class OperationStrategyTest {
    private static Map<FruitTransaction.Operation, TransactionHandler>
            transactionHandlerMap;

    @BeforeAll
    static void beforeAll() {
        transactionHandlerMap = new HashMap<>();
        transactionHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceTransactionImpl());
        transactionHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseTransactionImpl());
        transactionHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnTransactionImpl());
        transactionHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyTransactionImpl());
    }

    @AfterAll
    static void afterAll() {
        transactionHandlerMap.clear();
    }

    @Test
    void get_validBalanceTransactionImpl_ok() {
        assertTrue(transactionHandlerMap.get(FruitTransaction.Operation.BALANCE)
                instanceof BalanceTransactionImpl);
    }

    @Test
    void get_validPurchaseTransactionImpl_ok() {
        assertTrue(transactionHandlerMap.get(FruitTransaction.Operation.PURCHASE)
                instanceof PurchaseTransactionImpl);
    }

    @Test
    void get_validReturnTransactionImpl_ok() {
        assertTrue(transactionHandlerMap.get(FruitTransaction.Operation.RETURN)
                instanceof ReturnTransactionImpl);
    }

    @Test
    void get_validSupplyTransactionImpl_ok() {
        assertTrue(transactionHandlerMap.get(FruitTransaction.Operation.SUPPLY)
                instanceof SupplyTransactionImpl);
    }

    @Test
    void get_validOperationStrategy_ok() {
        assertEquals(transactionHandlerMap.get(FruitTransaction.Operation.BALANCE),
                new OperationStrategyImpl(transactionHandlerMap)
                        .get(FruitTransaction.Operation.BALANCE));
    }
}
