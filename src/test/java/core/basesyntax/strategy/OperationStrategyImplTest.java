package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImp;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.impl.BalanceOperationHandler;
import core.basesyntax.operation.impl.PurchaseOperationHandler;
import core.basesyntax.operation.impl.ReturnOperationHandler;
import core.basesyntax.operation.impl.SupplyOperationHandler;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.impl.FruitServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private FruitDao fruitDao;
    private FruitService fruitService;
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @Before
    public void setUp() throws Exception {
        fruitDao = new FruitDaoImp();
        fruitService = new FruitServiceImpl(fruitDao);
        operationHandlerMap = new HashMap<>();
        operationHandlerMap
                .put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(fruitService));
        operationHandlerMap
                .put(FruitTransaction.Operation.PURCHASE,
                        new PurchaseOperationHandler(fruitDao, fruitService));
        operationHandlerMap
                .put(FruitTransaction.Operation.RETURN,
                        new ReturnOperationHandler(fruitDao, fruitService));
        operationHandlerMap
                .put(FruitTransaction.Operation.SUPPLY,
                        new SupplyOperationHandler(fruitDao, fruitService));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void get_balance_ok() {
        Class<? extends OperationHandler> expected = BalanceOperationHandler.class;
        Class<? extends OperationHandler> actual
                = operationStrategy.get(FruitTransaction.Operation.BALANCE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_purchase_ok() {
        Class<? extends OperationHandler> expected = PurchaseOperationHandler.class;
        Class<? extends OperationHandler> actual
                = operationStrategy.get(FruitTransaction.Operation.PURCHASE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_return_Ok() {
        Class<? extends OperationHandler> expected = ReturnOperationHandler.class;
        Class<? extends OperationHandler> actual
                = operationStrategy.get(FruitTransaction.Operation.RETURN).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_supply_Ok() {
        Class<? extends OperationHandler> expected = SupplyOperationHandler.class;
        Class<? extends OperationHandler> actual
                = operationStrategy.get(FruitTransaction.Operation.SUPPLY).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_nullOperation_notOk() {
        assertThrows(RuntimeException.class, () -> {
            operationStrategy.get(null);
        });
    }

    @Test
    public void get_notExistedOperations_notOk() {
        operationHandlerMap.remove(FruitTransaction.Operation.RETURN);
        assertThrows(RuntimeException.class, () -> {
            operationStrategy.get(FruitTransaction.Operation.RETURN);
        });
    }
}
