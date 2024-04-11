package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private static FruitService service;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = Map.of(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
    }

    @BeforeEach
    void setUp() {
        service = new FruitServiceImpl(
                new OperationStrategyImpl(operationHandlerMap));
    }

    @Test
    void processActivitiesWithFruits_nullData_notOk() {
        assertThrows(NullPointerException.class, () ->
                service.processActivitiesWithFruits(null));
    }

    @Test
    void processActivitiesWithFruits_validData_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(60);
        List<FruitTransaction> fruitTransactions = List.of(fruitTransaction);
        service.processActivitiesWithFruits(fruitTransactions);
        int expected = 60;
        int actual = Storage.remainsOfFruits.get(fruitTransaction.getFruit());
        assertEquals(expected, actual, "Expected quantity of fruit \""
                + fruitTransaction.getFruit() + "\" is " + expected + ", but "
                + "actual is " + actual + ".");
    }

    @AfterEach
    void tearDown() {
        Storage.remainsOfFruits.clear();
    }
}
