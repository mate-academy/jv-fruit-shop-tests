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

class SupplyOperationTest {
    private static FruitDao fruitDao;
    private static OperationStrategy supplyOperation;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        supplyOperation = new SupplyOperation(fruitDao);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void supply_operation_success() {
        FruitTransaction fruitTransaction = new
                FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 40);
        supplyOperation.execute(fruitTransaction);
        int actualQuantity = storage.get("banana");
        int expected = 40;
        assertEquals(expected, actualQuantity);
    }

    @Test
    void supply_operationFruitExist_isOk() {
        String fruit = "banana";
        int quantity = 60;
        storage.put(fruit, quantity);
        FruitTransaction fruitTransaction = new
                FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 40);
        supplyOperation.execute(fruitTransaction);
        int actualQuantity = storage.get("banana");
        int expected = 100;
        assertEquals(expected, actualQuantity);
    }
}
