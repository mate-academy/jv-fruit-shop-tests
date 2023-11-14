package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.service.DataProcessor;
import core.basesyntax.service.OperationStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class DataProcessorImplTest {
    private static final String VALID_FRUIT = "Banana";
    private static final int VALID_QUANTITY = 20;
    private static final int INVALID_QUANTITY = 100;
    private final Storage storage = new Storage();
    private final FruitDao fruitDao = new FruitDaoImpl(storage);
    private final Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
    private final OperationStrategy operationStrategy = new OperationStrategyImpl(handlerMap);
    private final DataProcessor dataProcessor = new DataProcessorImpl(operationStrategy);
    private final List<FruitTransaction> fruitTransactions = new ArrayList<>();

    @AfterEach
    void tearDown() {
        storage.getFruits().clear();
        handlerMap.clear();
    }

    @Test
    void processData_balanceTransaction_isOk() {
        storage.getFruits().put(VALID_FRUIT, INVALID_QUANTITY);
        handlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(fruitDao));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                VALID_FRUIT, VALID_QUANTITY));
        dataProcessor.processData(fruitTransactions);
        assertEquals(VALID_QUANTITY, storage.getFruits().get(VALID_FRUIT).intValue());
    }
}
