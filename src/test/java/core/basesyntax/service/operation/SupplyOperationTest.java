package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static SupplyOperation supplyOperation;

    @BeforeAll
    static void beforeAll() {
        supplyOperation = new SupplyOperation();
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void operate_validData_ok() {
        Storage.STORAGE.put("banana", 50);
        FruitTransaction fruitTransaction = new FruitTransaction(
                OperationType.SUPPLY,"banana", 30);
        supplyOperation.operate(fruitTransaction);
        int actual = Storage.STORAGE.get("banana");
        assertEquals(80, actual);
    }
}
