package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static ReturnOperation returnOperation;

    @BeforeAll
    static void beforeAll() {
        returnOperation = new ReturnOperation();
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void operate_ValidData_Ok() {
        Storage.STORAGE.put("banana", 100);
        FruitTransaction fruitTransaction = new FruitTransaction(
                OperationType.RETURN, "banana", 30);
        returnOperation.operate(fruitTransaction);
        int actual = Storage.STORAGE.get("banana");
        assertEquals(130, actual);
    }
}
