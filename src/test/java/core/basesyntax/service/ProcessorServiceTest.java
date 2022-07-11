package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.impl.ProcessorServiceImpl;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.strategy.TransactionStrategyImpl;
import core.basesyntax.transaction.BalanceTransactionHandler;
import core.basesyntax.transaction.PurchaseTransactionHandler;
import core.basesyntax.transaction.ReturnTransactionHandler;
import core.basesyntax.transaction.SupplyTransactionHandler;
import core.basesyntax.transaction.TransactionHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProcessorServiceTest {
    private static FruitDao dao;
    private static TransactionStrategy strategy;
    private static ProcessorService processorService;

    @BeforeClass
    public static void beforeAll() {
        Map<String, TransactionHandler> transactionHandlersMap = new HashMap<>();
        transactionHandlersMap.put("b", new BalanceTransactionHandler());
        transactionHandlersMap.put("s", new SupplyTransactionHandler());
        transactionHandlersMap.put("p", new PurchaseTransactionHandler());
        transactionHandlersMap.put("r", new ReturnTransactionHandler());
        dao = new FruitDaoImpl();
        strategy = new TransactionStrategyImpl(transactionHandlersMap);
        processorService = new ProcessorServiceImpl(dao, strategy);
    }

    @Test
    public void processData_ok() {
        processorService.processData(List.of(
                "type,fruit,quantity",
                "b,apple,10",
                "b,banana,1",
                "b,orange,102",
                "b,grapefruit,102",
                "s,apple,100",
                "s,grapefruit,1001",
                "s,banana,50",
                "s,orange,3",
                "p,grapefruit,20",
                "p,apple,50",
                "p,banana,1",
                "p,orange,100",
                "r,apple,5",
                "r,banana,12",
                "r,orange,1",
                "r,grapefruit,2"));
        Assert.assertEquals(dao.get("apple"), 65);
        Assert.assertEquals(dao.get("banana"), 62);
        Assert.assertEquals(dao.get("orange"), 6);
        Assert.assertEquals(dao.get("grapefruit"), 1085);
    }

    @After
    public void afterEach() {
        Storage.fruitsAvailable.clear();
    }
}
