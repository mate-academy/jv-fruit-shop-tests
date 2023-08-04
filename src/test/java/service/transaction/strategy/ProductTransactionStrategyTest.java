package service.transaction.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.transaction.strategy.type.BalanceTransaction;
import service.transaction.strategy.type.PurchaseTransaction;
import service.transaction.strategy.type.ReturnTransaction;
import service.transaction.strategy.type.SupplyTransaction;
import service.transaction.strategy.type.TransactionHandler;

public class ProductTransactionStrategyTest {
    private static TransactionStrategy transactionStrategy;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.OperationType, TransactionHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.OperationType.BALANCE, new BalanceTransaction());
        handlers.put(FruitTransaction.OperationType.PURCHASE, new PurchaseTransaction());
        handlers.put(FruitTransaction.OperationType.SUPPLY, new SupplyTransaction());
        handlers.put(FruitTransaction.OperationType.RETURN, new ReturnTransaction());
        transactionStrategy = new ProductTransactionStrategy(handlers);
    }

    @Test
    void validCase_validOperationType_purchase() {
        TransactionHandler expected = new PurchaseTransaction();
        TransactionHandler actual = transactionStrategy.getHandler(
                FruitTransaction.OperationType.PURCHASE);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void validCase_validOperationType_supply() {
        TransactionHandler expected = new SupplyTransaction();
        TransactionHandler actual = transactionStrategy.getHandler(
                FruitTransaction.OperationType.SUPPLY);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void validCase_validOperationType_return() {
        TransactionHandler expected = new ReturnTransaction();
        TransactionHandler actual = transactionStrategy.getHandler(
                FruitTransaction.OperationType.RETURN);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void validCase_validOperationType_balance() {
        TransactionHandler expected = new BalanceTransaction();
        TransactionHandler actual = transactionStrategy.getHandler(
                FruitTransaction.OperationType.BALANCE);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void invalidCase_nullOperationType() {
        TransactionHandler actual = transactionStrategy.getHandler(null);
        assertNull(actual);
    }
}
