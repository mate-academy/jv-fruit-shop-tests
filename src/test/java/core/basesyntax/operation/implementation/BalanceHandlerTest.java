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

public class BalanceHandlerTest {
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final FruitService fruitService = new FruitServiceImpl(fruitDao);
    private final FruitTransaction fruitTransaction = new FruitTransaction();

    @After
    public void after() {
        Storage.fruits.clear();
    }

    @Test
    public void handleBalance_Ok() {
        fruitTransaction.setFruit("apple");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(17);

        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap =
                new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceHandler(fruitService));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyHandler(fruitService));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnHandler(fruitService));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler(fruitService));

        Strategy strategy = new StrategyImpl(operationHandlerMap);
        strategy.process(fruitTransaction.getOperation()).handle(fruitTransaction);
        int actual = Storage.fruits.get("apple");
        int expected = 17;
        Assert.assertEquals(expected, actual);
    }
}
