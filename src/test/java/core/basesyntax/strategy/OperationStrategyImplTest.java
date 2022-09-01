package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.exceptions.WrongDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operations.BalanceOperation;
import core.basesyntax.strategy.operations.DailyOperationHandler;
import core.basesyntax.strategy.operations.ReturnOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private BalanceOperation balanceOperation;
    private ReturnOperation returnOperation;
    private Map<FruitTransaction.Operation, DailyOperationHandler> operationHandlerMap;
    private FruitDao fruitDao;

    @Before
    public void setUp() throws Exception {
        fruitDao = new FruitDaoImpl();
        balanceOperation = new BalanceOperation(fruitDao);
        returnOperation = new ReturnOperation(fruitDao);
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                balanceOperation);
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                returnOperation);
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test(expected = WrongDataException.class)
    public void get_operationIsNull_notOk() {
        operationStrategy.get(null);
    }

    @Test
    public void get_operationIsCorrect_Ok() {
        DailyOperationHandler actual =
                operationStrategy.get(FruitTransaction.Operation.BALANCE);
        DailyOperationHandler expected = balanceOperation;
        assertEquals(expected,actual);
    }

    @Test
    public void get_operationIsNotCorrect_NotOk() {
        DailyOperationHandler actual =
                operationStrategy.get(FruitTransaction.Operation.RETURN);
        DailyOperationHandler expected = balanceOperation;
        assertNotEquals(expected,actual);
    }
}
