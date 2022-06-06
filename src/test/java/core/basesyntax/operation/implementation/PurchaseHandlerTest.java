package core.basesyntax.operation.implementation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.implementation.FruitServiceImpl;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.StrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseHandlerTest {
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final FruitService fruitService = new FruitServiceImpl(fruitDao);
    private final FruitTransaction fruitTransaction = new FruitTransaction();
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap =
            new HashMap<>();

    @After
    public void after() {
        Storage.fruits.clear();
    }

    @Test
    public void handlePurchase_Ok() {
        String fruitName = "banana";
        fruitDao.add(fruitName, 25);
        fruitTransaction.setQuantity(12);
        fruitTransaction.setFruit(fruitName);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);

        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler(fruitService));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnHandler(fruitService));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyHandler(fruitService));
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceHandler(fruitService));

        Strategy strategy = new StrategyImpl(operationHandlerMap);
        strategy.process(fruitTransaction.getOperation()).handle(fruitTransaction);
        int actual = Storage.fruits.get(fruitName);
        int expected = 13;
        Assert.assertEquals(expected, actual);
    }
}
