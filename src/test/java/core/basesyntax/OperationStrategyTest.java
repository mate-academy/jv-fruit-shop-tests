package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationProcessor;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.AddOperationProcessor;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.SubstractOperationProcessor;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static final String SUPPLY = "s";
    private static final String PURCHASE = ("p");

    private static Transaction.Operation addOperation;
    private static Transaction.Operation substractOperation;
    private static OperationProcessor addOperationProcessor;
    private static OperationProcessor substractOperationProcessor;
    private static OperationStrategy strategy;
    private static FruitDao fruitDao;
    private static Map<Transaction.Operation,
            OperationProcessor> operationProcessMap;

    @BeforeClass
    public static void before() {
        operationProcessMap = new HashMap<>();
        fruitDao = new FruitDaoImpl();
        addOperationProcessor = new AddOperationProcessor(fruitDao);
        substractOperationProcessor = new SubstractOperationProcessor(fruitDao);
        addOperation = Transaction.Operation.getByCode(SUPPLY);
        substractOperation = Transaction.Operation.getByCode(PURCHASE);
        operationProcessMap.put(addOperation, addOperationProcessor);
        operationProcessMap.put(substractOperation, substractOperationProcessor);
        strategy = new OperationStrategyImpl(operationProcessMap);
    }

    @Test
    public void operationStrategyForPurchase_Ok() {
        OperationProcessor expected = addOperationProcessor;
        OperationProcessor actual;
        actual = strategy.get(addOperation);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void operationStrategyForSupply_Ok() {
        OperationProcessor expected = substractOperationProcessor;
        OperationProcessor actual;
        actual = strategy.get(substractOperation);
        assertEquals(expected.getClass(), actual.getClass());
    }
}
