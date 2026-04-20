package core.basesyntax.strategy;

import core.basesyntax.checker.Checker;
import core.basesyntax.db.InMemoryStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionDao;
import core.basesyntax.service.impl.TransactionDaoImpl;
import core.basesyntax.strategy.handler.BalanceHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseHandler;
import core.basesyntax.strategy.handler.ReturnHandler;
import core.basesyntax.strategy.handler.SupplyHandler;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {

    private Storage storage;
    private Checker checker;
    private TransactionDao dao;
    private OperationStrategy strategy;

    @BeforeEach
    void setUp() {
        storage = new InMemoryStorage();
        checker = new Checker(storage);
        dao = new TransactionDaoImpl(storage, checker);

        Map<Transaction.Operation, OperationHandler> handlers = Map.of(
                Transaction.Operation.BALANCE, new BalanceHandler(dao),
                Transaction.Operation.SUPPLY, new SupplyHandler(dao),
                Transaction.Operation.PURCHASE, new PurchaseHandler(dao),
                Transaction.Operation.RETURN, new ReturnHandler(dao)
        );

        strategy = new OperationStrategyImpl(handlers);
    }
    
    @Test
    void getOperationHandler_allHandlers_ok() {
        Assertions.assertTrue(
                strategy.getOperationHandler(Transaction.Operation.BALANCE)
                        instanceof BalanceHandler);

        Assertions.assertTrue(
                strategy.getOperationHandler(Transaction.Operation.SUPPLY)
                        instanceof SupplyHandler);

        Assertions.assertTrue(
                strategy.getOperationHandler(Transaction.Operation.PURCHASE)
                        instanceof PurchaseHandler);

        Assertions.assertTrue(
                strategy.getOperationHandler(Transaction.Operation.RETURN)
                        instanceof ReturnHandler);
    }
}
