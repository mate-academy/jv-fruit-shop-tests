package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private ReturnOperation returnOperation;

    @BeforeEach
    void setUp() {
        returnOperation = new ReturnOperation();
    }

    @Test
    void getCalculation_invalidFruit_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            returnOperation.getCalculation(new FruitTransaction(
                    FruitTransaction.Operation.RETURN,"invalidFruit",6));
        });
    }

    @Test
    void getCalculation_addData_Ok() {
        Storage.storage.put("banana", 0);
        returnOperation.getCalculation(new FruitTransaction(
                FruitTransaction.Operation.RETURN,"banana",3));
        assertEquals(3, Storage.storage.get("banana"));
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}
