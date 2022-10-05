package core.basesyntax.tests;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseHandler;
import core.basesyntax.operation.ReturnHandler;
import core.basesyntax.operation.SupplyHandler;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private OperationHandler operationHandlerBalance;
    private OperationHandler operationHandlerPurchase;
    private OperationHandler operationHandlerReturn;
    private OperationHandler operationHandlerSupply;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @Before
    public void setUp() throws Exception {
        operationHandlerMap = new HashMap<>();
        FruitsDao fruitsDao = new FruitDaoImpl();
        operationHandlerBalance = new BalanceHandler(fruitsDao);
        operationHandlerPurchase = new PurchaseHandler(fruitsDao);
        operationHandlerReturn = new ReturnHandler(fruitsDao);
        operationHandlerSupply = new SupplyHandler(fruitsDao);
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, operationHandlerBalance);
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, operationHandlerPurchase);
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, operationHandlerReturn);
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, operationHandlerSupply);
    }

    @Test
    public void operationStrategy_CorrectData_Ok() {
        assertEquals(operationStrategy
                .get(FruitTransaction.Operation.BALANCE), operationHandlerBalance);
        assertEquals(operationStrategy
                .get(FruitTransaction.Operation.PURCHASE), operationHandlerPurchase);
        assertEquals(operationStrategy
                .get(FruitTransaction.Operation.RETURN), operationHandlerReturn);
        assertEquals(operationStrategy
                .get(FruitTransaction.Operation.SUPPLY), operationHandlerSupply);
    }

    @After
    public void tearDown() throws Exception {
        operationHandlerMap.clear();
        Storage.fruitsStorage.clear();
    }
}
