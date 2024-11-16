package core.basesyntax.service.handler.impl;

import core.basesyntax.dao.FruitRepository;
import core.basesyntax.dao.impl.FruitRepositoryImpl;
import core.basesyntax.db.Database;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.enums.Operation;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handler.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class BaseOperationHandlerTest {
    protected static final String BANANA = "banana";
    protected static OperationStrategy operationStrategy;
    protected Map<String, Integer> db = Database.storage;

    @BeforeAll
    static void beforeAll() {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        FruitRepository fruitRepository = new FruitRepositoryImpl();
        operationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandler(fruitRepository));
        operationHandlerMap.put(Operation.SUPPLY, new SupplyOperationHandler(fruitRepository));
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler(fruitRepository));
        operationHandlerMap.put(Operation.RETURN, new ReturnOperationHandler(fruitRepository));

        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @AfterEach
    void tearDown() {
        db.clear();
    }

    public void handleTransaction(FruitTransaction transaction) {
        OperationHandler handler = operationStrategy
                .getOperationHandler(transaction.getOperation());
        handler.handle(transaction);
    }
}
