package basesyntax.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import basesyntax.model.FruitTransaction;
import basesyntax.model.Operation;
import basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private OperationHandler returnOperation;

    @BeforeEach
    void setUp() {
        returnOperation = new ReturnOperation();
    }

    @Test
    void handle_validReturnOperation_Ok() {
        Storage.put("apple", 12);
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "apple", 5);
        returnOperation.handle(transaction);
        assertEquals(17, Storage.get("apple"));
    }

    @Test
    void handle_moreThenOneValidReturnOperation_Ok() {
        Storage.put("banana", 2);
        FruitTransaction transaction1 = new FruitTransaction(Operation.RETURN, "banana", 7);
        returnOperation.handle(transaction1);
        assertEquals(9, Storage.get("banana"));

        FruitTransaction transaction2 = new FruitTransaction(Operation.RETURN, "banana", 1);
        returnOperation.handle(transaction2);
        assertEquals(10, Storage.get("banana"));
    }

    @Test
    void handle_validReturnOperationToEmptyStorage_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "kiwi", 2);
        returnOperation.handle(transaction);
        assertEquals(2, Storage.get("kiwi"));
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }
}
