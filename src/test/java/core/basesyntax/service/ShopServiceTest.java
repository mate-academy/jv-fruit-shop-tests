package core.basesyntax.service;

import core.basesyntax.bd.Storage;
import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.service.operationhandler.OperationHandler;
import core.basesyntax.service.operationhandler.impl.AddingOperationHandler;
import core.basesyntax.service.operationhandler.impl.RemovingOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShopServiceTest {
    private static ShopService shopService;
    private static final Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
    private static final List<TransactionDto> transactionDtos = new ArrayList<>();

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        operationHandlerMap.put(Operation.BALANCE, new AddingOperationHandler(fruitDao));
        operationHandlerMap.put(Operation.PURCHASE, new RemovingOperationHandler(fruitDao));
        operationHandlerMap.put(Operation.SUPPLY, new AddingOperationHandler(fruitDao));
        operationHandlerMap.put(Operation.RETURN, new AddingOperationHandler(fruitDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        shopService = new ShopServiceImpl(operationStrategy, fruitDao);

    }

    @Before
    public void setUp() {
        Storage.fruitStorage.clear();
        transactionDtos.clear();
        transactionDtos.add(new TransactionDto(Operation.BALANCE, new Fruit("banana"), 20));
        transactionDtos.add(new TransactionDto(Operation.BALANCE, new Fruit("apple"), 100));
        transactionDtos.add(new TransactionDto(Operation.SUPPLY, new Fruit("banana"), 70));
        transactionDtos.add(new TransactionDto(Operation.PURCHASE, new Fruit("apple"), 17));
        transactionDtos.add(new TransactionDto(Operation.PURCHASE, new Fruit("banana"), 55));
        transactionDtos.add(new TransactionDto(Operation.RETURN, new Fruit("apple"), 12));
    }

    @Test
    public void updateStorage_Ok() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 35);
        expected.put(new Fruit("apple"), 95);
        Map<Fruit, Integer> actual = shopService.updateStorageInfo(transactionDtos);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void updateStorageWithNotEnoughFruits_NotOk() {
        transactionDtos.add(new TransactionDto(Operation.PURCHASE, new Fruit("banana"), 55));
        shopService.updateStorageInfo(transactionDtos);
    }

    @Test(expected = NullPointerException.class)
    public void updateStorageNull_notOk() {
        shopService.updateStorageInfo(null);
    }
}
