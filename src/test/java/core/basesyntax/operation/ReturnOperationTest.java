package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private final OperationHandler returnOperation = new ReturnOperation();

    @BeforeEach
    void setUp() {
        Storage.storage.put("banana", 20);
    }

    @Test
    void processOperation_returnExistentFruit_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 30);
        returnOperation.processOperation(fruitTransaction);
        int expectedAmountOfBanana = 50;
        int actual = Storage.storage.get("banana");
        assertEquals(expectedAmountOfBanana, actual);
    }

    @Test
    void processOperation_returnNewFruit_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 100);
        returnOperation.processOperation(fruitTransaction);
        Map<String, Integer> expected = Map.of(
                "banana", 20,
                "apple", 100);
        Map<String, Integer> actual = Storage.storage;
        assertEquals(expected, actual);
    }

    @Test
    void processOperation_returnNegativeAmount_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", -30);
        assertThrows(RuntimeException.class, () ->
                returnOperation.processOperation(fruitTransaction));
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}
