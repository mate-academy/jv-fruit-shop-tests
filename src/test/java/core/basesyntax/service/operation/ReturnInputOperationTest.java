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

class ReturnInputOperationTest {
    private static InputOperation returnInputTransaction;

    @BeforeAll
    static void beforeAll() {
        returnInputTransaction = new ReturnInputOperation(new FruitTransactionDaoImpl());
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
                FruitTransaction.Operation.RETURN, "banana", 30);
        returnInputTransaction.process(fruitTransaction);
        Integer expected = fruitTransaction.getQuantity();
        Integer actual = Storage.fruitsStorage.get(fruitTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @Test
    void process_fruitTransactionIsNull_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            returnInputTransaction.process(null);
        });
        String expected = "Fruit operation must not be null.";
        String actual = runtimeException.getMessage();
        assertEquals(expected, actual);
    }
}
