package core.basesyntax.strategy;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.AdditionalOperation;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.SubtractionOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static FruitDao fruitDao;
    private static Storage storage;
    private static Map<FruitTransaction.Operation, OperationHandler> operationsMap;

    @BeforeClass
    public static void beforeClass() {
        storage = new Storage();
        fruitDao = new FruitDaoImpl(storage);
        operationsMap = new HashMap<>();
        operationsMap.put(BALANCE, new AdditionalOperation(fruitDao));
        operationsMap.put(SUPPLY, new AdditionalOperation(fruitDao));
        operationsMap.put(PURCHASE, new SubtractionOperation(fruitDao));
        operationsMap.put(RETURN, new AdditionalOperation(fruitDao));
    }

    @Test
    public void getOperationType_ifParameterCorrect_Ok() {
        OperationStrategy operationStrategy = new OperationStrategy(operationsMap);
        for (FruitTransaction.Operation key: operationsMap.keySet()) {
            OperationHandler operationType = operationStrategy.getOperationType(key);
            boolean actual = operationType instanceof OperationHandler;
            assertTrue(actual);
        }
    }

    @Test
    public void getOperationType_ifParameterMethodNull_NotOk() {
        OperationStrategy operationStrategy = new OperationStrategy(operationsMap);
        OperationHandler operationType = operationStrategy.getOperationType(null);
        boolean actual = operationType instanceof OperationHandler;
        assertFalse(actual);
    }
}
