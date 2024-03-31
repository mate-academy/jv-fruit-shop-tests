package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.activity.BalanceTransactionHandler;
import core.basesyntax.service.activity.PurchaseTransactionHandler;
import core.basesyntax.service.activity.ReturnTransactionHandler;
import core.basesyntax.service.activity.SupplyTransactionHandler;
import core.basesyntax.service.activity.TransactionHandler;
import core.basesyntax.strategy.ActivityStrategy;
import core.basesyntax.strategy.ActivityStrategyImpl;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ActivityStrategyTest {
    private static ActivityStrategy activityStrategy;
    private static FruitDaoImpl fruitDao;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, TransactionHandler> activityHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceTransactionHandler(fruitDao),
                FruitTransaction.Operation.PURCHASE, new PurchaseTransactionHandler(fruitDao),
                FruitTransaction.Operation.RETURN, new ReturnTransactionHandler(fruitDao),
                FruitTransaction.Operation.SUPPLY, new SupplyTransactionHandler(fruitDao));
        activityStrategy = new ActivityStrategyImpl(activityHandlerMap);
    }

    @Test
    void get_nullOperation_notOk() {
        assertThrows(NullPointerException.class, () -> activityStrategy.get(null));
    }

    @Test
    void get_normalOperations_ok() {
        TransactionHandler transactionHandler = new BalanceTransactionHandler(fruitDao);
        assertEquals(transactionHandler, activityStrategy
                .get(FruitTransaction.Operation.BALANCE));
        transactionHandler = new PurchaseTransactionHandler(fruitDao);
        assertEquals(transactionHandler, activityStrategy
                .get(FruitTransaction.Operation.PURCHASE));
        transactionHandler = new ReturnTransactionHandler(fruitDao);
        assertEquals(transactionHandler, activityStrategy
                .get(FruitTransaction.Operation.RETURN));
        transactionHandler = new SupplyTransactionHandler(fruitDao);
        assertEquals(transactionHandler, activityStrategy
                .get(FruitTransaction.Operation.SUPPLY));
    }
}
