package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyInputOperationTest {
    private static InputOperation supplyInputTransaction;

    @BeforeAll
    static void beforeAll() {
        supplyInputTransaction = new SupplyInputOperation(new FruitTransactionDaoImpl());
    }

    @BeforeEach
    void setUp() {
        Storage.fruitsStorage.put("banana", 100);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsStorage.clear();
    }

    @Test
    void process_validFruitTransaction_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 30);
        supplyInputTransaction.process(fruitTransaction);
        Integer expected = fruitTransaction.getQuantity();
        Integer actual = Storage.fruitsStorage.get(fruitTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @Test
    void process_fruitTransactionIsNull_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            supplyInputTransaction.process(null);
        });
        String expected = "Fruit operation must not be null.";
        String actual = runtimeException.getMessage();
        assertEquals(expected, actual);
    }
}
