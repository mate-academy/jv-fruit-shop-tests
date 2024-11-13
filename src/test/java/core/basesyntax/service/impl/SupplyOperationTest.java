package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.storage;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private FruitDao fruitDao = new FruitDaoImpl();
    private OperationStrategy supplyOperation = new SupplyOperation(fruitDao);

    @BeforeEach
    void setUp() {
        storage.clear();
    }

    @Test
    void supply_operation_success() {
        FruitTransaction fruitTransaction = new
                FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 40);
        supplyOperation.execute(fruitTransaction);
        int actualQuantity = storage.get("banana");
        int expected = 40;
        Assertions.assertEquals(expected, actualQuantity);
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
        Assertions.assertEquals(expected, actualQuantity);
    }
}
