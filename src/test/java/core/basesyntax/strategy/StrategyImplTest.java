package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.implementation.BalanceHandler;
import core.basesyntax.operation.implementation.PurchaseHandler;
import core.basesyntax.operation.implementation.ReturnHandler;
import core.basesyntax.operation.implementation.SupplyHandler;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.implementation.FruitServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StrategyImplTest {
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final FruitService fruitService = new FruitServiceImpl(fruitDao);
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap
            = new HashMap<>();
    private final Strategy strategy = new StrategyImpl(operationHandlerMap);

    @Before
    public void setUp() {
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceHandler(fruitService));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyHandler(fruitService));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnHandler(fruitService));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler(fruitService));
    }

    @Test
    public void process_Balance_Ok() {
        Assert.assertEquals(strategy.process(FruitTransaction.Operation.BALANCE).getClass(),
                BalanceHandler.class);
    }

    @Test
    public void process_Supply_Ok() {
        Assert.assertEquals(strategy.process(FruitTransaction.Operation.SUPPLY).getClass(),
                SupplyHandler.class);
    }

    @Test
    public void process_Return_Ok() {
        Assert.assertEquals(strategy.process(FruitTransaction.Operation.RETURN).getClass(),
                ReturnHandler.class);
    }

    @Test
    public void process_Purchase_Ok() {
        Assert.assertEquals(strategy.process(FruitTransaction.Operation.PURCHASE).getClass(),
                PurchaseHandler.class);
    }

    @Test(expected = RuntimeException.class)
    public void process_nonExistentOperationType() {
        strategy.process(FruitTransaction.Operation.findByLetter("w"));
    }
}
