package core.basesyntax.operation.implementation;

import static org.junit.Assert.assertEquals;

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
import org.junit.Test;

public class ReturnHandlerTest {
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
    public void handleSupply_Ok() {
        String fruitName = "banana";
        fruitDao.add(fruitName, 10);
        fruitTransaction.setQuantity(12);
        fruitTransaction.setFruit(fruitName);
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);

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
        int actual = Storage.fruits.get(fruitTransaction.getFruit());
        int expected = 22;
        assertEquals(expected, actual);
    }
}
