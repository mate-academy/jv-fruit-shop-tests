package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Operations;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.impl.BalanceOperationHandler;
import core.basesyntax.service.strategy.impl.OperationStrategyImpl;
import core.basesyntax.service.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.service.strategy.impl.ReturnOperationHandler;
import core.basesyntax.service.strategy.impl.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionServiceImplTest {
    private static OperationStrategy operationStrategy;
    private static FruitTransactionService fruitTransactionService;
    private static FruitShopDao fruitShopDao;
    private static HashMap<String, OperationHandler> operationHandlerHashMap;

    @BeforeClass
    public static void beforeClass() {
        operationHandlerHashMap = new HashMap<>();
        operationHandlerHashMap.put(Operations.BALANCE.getOperation(),
                new BalanceOperationHandler());
        operationHandlerHashMap.put(Operations.SUPPLY.getOperation(),
                new SupplyOperationHandler());
        operationHandlerHashMap.put(Operations.PURCHASE.getOperation(),
                new PurchaseOperationHandler());
        operationHandlerHashMap.put(Operations.RETURN.getOperation(),
                new ReturnOperationHandler());

        operationStrategy = new OperationStrategyImpl(operationHandlerHashMap);
        fruitTransactionService = new FruitTransactionServiceImpl(operationStrategy);
        fruitShopDao = new FruitShopDaoImpl();
    }

    @Test
    public void fruitTransactionService_processTransactions_Ok() {
        List<String> listFruits = new ArrayList<>();
        listFruits.add("type,fruit,quantity");
        listFruits.add("b,apple,100");
        listFruits.add("s,banana,200");
        listFruits.add("p,apple,20");
        fruitTransactionService.process(listFruits);

        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 80);
        expected.put("banana", 200);

        Map<String, Integer> actual = fruitShopDao.getAll();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.fruitStorage.clear();
    }
}
