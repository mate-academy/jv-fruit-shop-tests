package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.storage;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static FruitDao fruitDao;
    private static OperationStrategy returnOperation;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        returnOperation = new ReturnOperation(fruitDao);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void return_operation_successful() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 25);
        returnOperation.execute(fruitTransaction);
        int actual = storage.get("banana");
        int expected = 25;
        assertEquals(expected, actual);
    }

    @Test
    void return_dataExist_successful() {
        storage.put("banana", 25);
        FruitTransaction fruitTransaction = new
                FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 25);
        returnOperation.execute(fruitTransaction);
        int actual = storage.get("banana");
        int expected = 50;
        assertEquals(expected, actual);
    }
}
