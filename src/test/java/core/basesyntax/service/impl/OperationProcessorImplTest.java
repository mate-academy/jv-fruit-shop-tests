package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationProcessor;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class OperationProcessorImplTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final List<FruitTransaction> fruitTransactions = List.of(
            new FruitTransaction(BALANCE, "banana", 20),
            new FruitTransaction(SUPPLY, "apple", 100),
            new FruitTransaction(PURCHASE, "banana", 13),
            new FruitTransaction(RETURN, "banana", 10));
    private static final FruitDao fruitDao = new FruitDaoImpl();
    private static final Map<FruitTransaction.Operation, OperationHandler> operationStrategies = Map
            .of(
            FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(fruitDao),
            FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(fruitDao),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(fruitDao),
            FruitTransaction.Operation.RETURN, new ReturnOperationHandler(fruitDao)
    );
    private static final OperationProcessor operationProcessor
            = new OperationProcessorImpl(operationStrategies);

    @Test
    void process_validData_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 17);
        expected.put("apple", 100);
        operationProcessor.process(fruitTransactions);
        Map<String, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    void process_nullData_notOk() {
        assertThrows(NullPointerException.class,
                () -> operationProcessor.process(null));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
