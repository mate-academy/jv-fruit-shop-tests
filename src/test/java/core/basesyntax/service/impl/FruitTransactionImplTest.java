package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.strategy.BalanceHandler;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.PurchaseHandler;
import core.basesyntax.service.strategy.ReturnHandler;
import core.basesyntax.service.strategy.SupplyHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionImplTest {
    private static final String TEST_FILE_PATH = "src/test/java/core/basesyntax/testfile";
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
    void processBalance_validOperation_returnsList() {
        assertDoesNotThrow(() -> {
            List<String> inputLines = Files.readAllLines(Path.of(TEST_FILE_PATH));
        }, "Error reading file: " + TEST_FILE_PATH);
        List<String> result = fruitTransaction.processBalance();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.get(0).contains("b"));
    }

    @Test
    void processBalance_validOperation_returnsL() {
        List<String> inputLines;
        try {
            inputLines = Files.readAllLines(Path.of(TEST_FILE_PATH));
        } catch (Exception e) {
            fail("Can't get data from file " + TEST_FILE_PATH, e);
            return;
        }
        List<String> result = fruitTransaction.processBalance();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.get(0).contains("b"));
    }

    @Test
    void processPurchase_validOperation_returnsList() {
        FruitTransactionImpl fruitTransaction = new FruitTransactionImpl(operationStrategy);
        List<String> result = fruitTransaction.processPurchase();
        assertNotNull(result);
    }

    @Test
    void processReturn_validOperation_returnsList() {
        FruitTransactionImpl fruitTransaction = new FruitTransactionImpl(operationStrategy);
        List<String> result = fruitTransaction.processReturn();
        assertNotNull(result);
    }

    @Test
    void processSupply_validOperation_returnsList() {
        FruitTransactionImpl fruitTransaction = new FruitTransactionImpl(operationStrategy);
        List<String> result = fruitTransaction.processSupply();
        assertNotNull(result);
    }
}
