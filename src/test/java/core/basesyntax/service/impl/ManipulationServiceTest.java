package core.basesyntax.service.impl;

import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ManipulationService;
import core.basesyntax.service.ReportService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.PurchaseOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.StrategyImpl;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ManipulationServiceTest {
    private static ManipulationService manipulationService;
    private static final List<Transaction> transactionList;

    static {
        transactionList = new ArrayList<>();
        transactionList.add(new Transaction(Transaction.Operation.BALANCE,
                new Fruit("banana"), 20));
        transactionList.add(new Transaction(Transaction.Operation.BALANCE,
                new Fruit("apple"), 100));
        transactionList.add(new Transaction(Transaction.Operation.SUPPLY,
                new Fruit("banana"), 100));
        transactionList.add(new Transaction(Transaction.Operation.PURCHASE,
                new Fruit("banana"), 13));
        transactionList.add(new Transaction(Transaction.Operation.RETURN,
                new Fruit("apple"), 10));
        transactionList.add(new Transaction(Transaction.Operation.PURCHASE,
                new Fruit("apple"), 20));
        transactionList.add(new Transaction(Transaction.Operation.PURCHASE,
                new Fruit("banana"), 5));
        transactionList.add(new Transaction(Transaction.Operation.SUPPLY,
                new Fruit("banana"), 50));
    }

    @BeforeClass
    public static void beforeClass() {
        Map<Transaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Transaction.Operation.BALANCE, new BalanceOperation());
        operationHandlerMap.put(Transaction.Operation.SUPPLY, new SupplyOperation());
        operationHandlerMap.put(Transaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlerMap.put(Transaction.Operation.RETURN, new ReturnOperation());
        Strategy strategy = new StrategyImpl(operationHandlerMap);
        manipulationService = new ManipulationServiceImpl(strategy);
        ReportService reportService = new ReportServiceImpl();
    }

    @Test
    public void manipulationData_emptyData_notOk() {
        List<Transaction> emptyList = new ArrayList<>();
        try {
            manipulationService.manipulation(emptyList);
        } catch (RuntimeException e) {
            return;
        }
        fail("Runtime Exception");
    }

    @Test
    public void manipulationData_correctData_ok() {
        Map<Fruit, Integer> expectedData = new HashMap<>();
        expectedData.put(new Fruit("banana"), 152);
        expectedData.put(new Fruit("apple"), 90);
        Map<Fruit, Integer> actualData = Storage.fruits;
        manipulationService.manipulation(transactionList);
        Assert.assertEquals(expectedData.toString(), actualData.toString());
    }
}

