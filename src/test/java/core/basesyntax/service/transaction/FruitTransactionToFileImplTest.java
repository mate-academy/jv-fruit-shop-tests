package core.basesyntax.service.transaction;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandlerImpl;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionToFileImplTest {
    private ArrayList<FruitTransaction> fruitTransactions;
    private FruitShopDao fruitShopDao;
    private OperationHandler operationHandler;
    private Map<Operation, OperationHandler> operationHandlerMap;
    private OperationStrategy operationStrategy;
    private FruitTransactionToFile fruitTransactionToFile;

    @BeforeEach
    void setUp() {
        Storage.fruitsDB.clear();
        fruitShopDao = new FruitShopDaoImpl();
        operationHandler = new ReturnOperationHandlerImpl(fruitShopDao);

        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.PURCHASE, operationHandler);
        operationHandlerMap.put(Operation.SUPPLY, operationHandler);
        operationHandlerMap.put(Operation.BALANCE, operationHandler);
        operationHandlerMap.put(Operation.RETURN, operationHandler);
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);

        fruitTransactionToFile = new FruitTransactionToFileImpl(operationStrategy);

        fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction(Operation.SUPPLY, "apple", 30));
    }

    @Test
    void fruitTransactionsIsWork_ok() {
        Storage.fruitsDB.put("apple", 100);
        Assertions.assertDoesNotThrow(() -> {
            fruitTransactionToFile.processTransaction(fruitTransactions);
        });
    }
}
