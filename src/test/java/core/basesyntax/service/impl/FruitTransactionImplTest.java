package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.strategy.BalanceHandler;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.PurchaseHandler;
import core.basesyntax.service.strategy.ReturnHandler;
import core.basesyntax.service.strategy.SupplyHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionImplTest {
    private OperationStrategy operationStrategy;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        operationStrategy = new OperationStrategyImpl(initializeHandlers());
        fruitTransaction = new FruitTransactionImpl(operationStrategy);
    }

    private static Map<Fruit.Operation, OperationHandler> initializeHandlers() {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<Fruit.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(Fruit.Operation.BALANCE, new BalanceHandler(fruitDao));
        handlers.put(Fruit.Operation.SUPPLY, new SupplyHandler(fruitDao));
        handlers.put(Fruit.Operation.PURCHASE, new PurchaseHandler(fruitDao));
        handlers.put(Fruit.Operation.RETURN, new ReturnHandler(fruitDao));
        return handlers;
    }

    @Test
    void processBalanceIs_Ok() {
        List<String> result = fruitTransaction.processBalance();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.get(0).contains("b"));
    }

    @Test
    void processPurchaseIs_Ok() {
        List<String> result = fruitTransaction.processPurchase();
        assertNotNull(result);
    }

    @Test
    void processReturnIs_Ok() {
        List<String> result = fruitTransaction.processReturn();
        assertNotNull(result);
    }

    @Test
    void processSupplyIs_Ok() {
        List<String> result = fruitTransaction.processSupply();
        assertNotNull(result);
    }
}
