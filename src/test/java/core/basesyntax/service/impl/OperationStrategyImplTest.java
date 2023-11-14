package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.service.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private final Storage storage = new Storage();
    private final FruitDao fruitDao = new FruitDaoImpl(storage);
    private final Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
    private final OperationStrategy operationStrategy = new OperationStrategyImpl(handlerMap);

    @AfterEach
    public void afterEachTest() {
        handlerMap.clear();
    }

    @Test
    void getOperationHandler_inMapOperation_isOk() {
        handlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(fruitDao));
        OperationHandler handler =
                operationStrategy.getOperationHandler(FruitTransaction.Operation.BALANCE);
        assertEquals(BalanceOperationHandler.class, handler.getClass());
    }

    @Test
    public void getOperationHandler_notInMapOperation_isNotOk() {
        handlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(fruitDao));
        assertNull(operationStrategy
                .getOperationHandler(FruitTransaction.Operation.PURCHASE));
    }

    @Test
    public void getOperationHandler_nullOperation_isNotOk() {
        handlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(fruitDao));
        assertNull(operationStrategy.getOperationHandler(null));
    }
}
