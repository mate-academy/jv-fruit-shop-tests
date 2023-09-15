package strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import strategy.impl.BalanceTransactionImpl;
import strategy.impl.PurchaseTransactionImpl;
import strategy.impl.ReturnTransactionImpl;
import strategy.impl.SupplyTransactionImpl;

class OperationStrategyTest {
    private static final Map<FruitTransaction.Operation, TransactionHandler>
            transactionHandlerMap = new HashMap<>();

    @AfterEach
    void tearDown() {
        transactionHandlerMap.clear();
    }

    @Test
    void get_valid_ok() {
        transactionHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceTransactionImpl());
        transactionHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseTransactionImpl());
        transactionHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnTransactionImpl());
        transactionHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyTransactionImpl());
        assertTrue(transactionHandlerMap.get(FruitTransaction.Operation.BALANCE)
                instanceof BalanceTransactionImpl);
        assertTrue(transactionHandlerMap.get(FruitTransaction.Operation.PURCHASE)
                instanceof PurchaseTransactionImpl);
        assertTrue(transactionHandlerMap.get(FruitTransaction.Operation.RETURN)
                instanceof ReturnTransactionImpl);
        assertTrue(transactionHandlerMap.get(FruitTransaction.Operation.SUPPLY)
                instanceof SupplyTransactionImpl);
    }
}
