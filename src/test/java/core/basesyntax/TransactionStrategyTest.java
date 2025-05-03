package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.TransactionStrategy;
import core.basesyntax.services.TransactionStrategyImpl;
import core.basesyntax.services.transaction.BalanceOperationHandler;
import core.basesyntax.services.transaction.OperationHandler;
import core.basesyntax.services.transaction.PurchaseOperationHandler;
import core.basesyntax.services.transaction.ReturnOperationHandler;
import core.basesyntax.services.transaction.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionStrategyTest {
    private static TransactionStrategy transactionStrategy;

    @BeforeClass
    public static void beforeClass() {
        Map<FruitTransaction.Operation, OperationHandler> transactionMap = new HashMap<>();
        transactionMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        transactionMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        transactionMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        transactionMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        transactionStrategy = new TransactionStrategyImpl(transactionMap);
    }

    @Test
    public void get_BalanceOperation_ok() {
        assertEquals(BalanceOperationHandler.class,
                transactionStrategy.get(FruitTransaction.Operation.BALANCE).getClass());
    }

    @Test
    public void get_SupplyOperation_ok() {
        FruitTransaction actualTransactionHandler =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,"banana,",100);
        OperationHandler expected = new SupplyOperationHandler();
        assertEquals(expected.getClass(),
                transactionStrategy.get(actualTransactionHandler.getOperation()).getClass());
    }

    @Test
    public void get_PurchaseOperation_ok() {
        FruitTransaction actualTransactionHandler =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,"banana,",13);
        OperationHandler expected = new PurchaseOperationHandler();
        assertEquals(expected.getClass(),
                transactionStrategy.get(actualTransactionHandler.getOperation()).getClass());
    }

    @Test
    public void get_ReturnOperation_ok() {
        FruitTransaction actualTransactionHandler =
                new FruitTransaction(FruitTransaction.Operation.RETURN,"apple,",10);
        OperationHandler expected = new ReturnOperationHandler();
        assertEquals(expected.getClass(),
                transactionStrategy.get(actualTransactionHandler.getOperation()).getClass());
    }
}
