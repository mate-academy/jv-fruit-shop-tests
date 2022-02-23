package core.basesyntax.initializer;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.impl.BalanceOperationImpl;
import core.basesyntax.service.operation.impl.PurchaseOperationImpl;
import core.basesyntax.service.operation.impl.ReturnOperationImpl;
import core.basesyntax.service.operation.impl.SupplyOperationImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class InitializerImplTest {
    private static final String EMPTY_PATH = "non path";
    private final Map<String, Integer> expectedMap;
    private final FruitDao<String, Integer> fruitDao;
    private final InitializerImpl initializer;

    public InitializerImplTest() {
        expectedMap = new HashMap<>();
        expectedMap.put("banana", 152);
        expectedMap.put("apple", 90);
        fruitDao = new FruitDaoImpl(new Storage());
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap
                .put(FruitTransaction.Operation.BALANCE, new BalanceOperationImpl(fruitDao));
        operationHandlerMap
                .put(FruitTransaction.Operation.SUPPLY, new SupplyOperationImpl(fruitDao));
        operationHandlerMap
                .put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationImpl(fruitDao));
        operationHandlerMap
                .put(FruitTransaction.Operation.RETURN, new ReturnOperationImpl(fruitDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        initializer = new InitializerImpl(operationStrategy);
    }

    @Test
    public void initStorage_Ok() {
        initializer.initStorage("input-file.csv");
        assertEquals(expectedMap.entrySet(), fruitDao.getAll().entrySet());
    }

    @Test(expected = RuntimeException.class)
    public void initStorage_notOk() {
        initializer.initStorage(EMPTY_PATH);
    }
}
