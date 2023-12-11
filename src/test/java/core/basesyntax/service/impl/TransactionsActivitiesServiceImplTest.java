package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionsActivitiesService;
import core.basesyntax.strategy.OperationOption;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.operationhandlers.BalanceOperationHandler;
import core.basesyntax.strategy.operationhandlers.OperationsHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionsActivitiesServiceImplTest {

    private TransactionsActivitiesService transactionsActivitiesService;

    @BeforeEach
    public void setUp() {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationsHandler> handlers = new HashMap<>();
        BalanceOperationHandler balanceOperationHandler = new BalanceOperationHandler(fruitDao);
        handlers.put(FruitTransaction.Operation.BALANCE, balanceOperationHandler);
        OperationStrategy operationStrategy = new OperationStrategy(handlers);
        OperationOption operationOption = operationStrategy;

        transactionsActivitiesService = new TransactionsActivitiesServiceImpl(operationOption);
    }

    @Test
    public void activityOfTransaction_ValidTransactions_CallsHandlers() {
        FruitTransaction balanceTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "Apple", 10);
        List<FruitTransaction> transactions = List.of(balanceTransaction);

        transactionsActivitiesService.activityOfTransaction(transactions);
    }
}
