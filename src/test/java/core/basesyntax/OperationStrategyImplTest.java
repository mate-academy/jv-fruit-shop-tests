package core.basesyntax;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.hadler.OperationHandler;
import core.basesyntax.hadler.impl.BalanceOperationHandler;
import core.basesyntax.hadler.impl.DecreaseOperationHandler;
import core.basesyntax.hadler.impl.IncreaseOperationHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;

    @Before
    public void setup() {
        Map<Operation, OperationHandler> handlerMap = new HashMap<>();
        StorageDao storageDao = new StorageDaoImpl();
        handlerMap.put(BALANCE, new BalanceOperationHandler(storageDao));
        handlerMap.put(SUPPLY, new IncreaseOperationHandler(storageDao));
        handlerMap.put(RETURN, new IncreaseOperationHandler(storageDao));
        handlerMap.put(PURCHASE, new DecreaseOperationHandler(storageDao));
        operationStrategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    public void get_CorrectHandler_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(BALANCE, "apple", 0),
                new FruitTransaction(SUPPLY, "apple", 0),
                new FruitTransaction(RETURN, "apple", 0),
                new FruitTransaction(PURCHASE, "apple", 0)
        );
        StorageDao storageDao = new StorageDaoImpl();
        List<OperationHandler> operationHandlers = List.of(
                new BalanceOperationHandler(storageDao),
                new IncreaseOperationHandler(storageDao),
                new IncreaseOperationHandler(storageDao),
                new DecreaseOperationHandler(storageDao)
        );
        for (int i = 0; i < 4; i++) {
            assertEquals(operationHandlers.get(i).getClass(),
                    operationStrategy.get(transactions.get(i).getOperation()).getClass());
        }
    }

    @Test
    public void get_NonExistingOperation_ReturnNull() {
        OperationHandler operationHandler = operationStrategy.get(null);
        assertTrue(operationHandler == null);
    }
}
