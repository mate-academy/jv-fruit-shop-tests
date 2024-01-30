package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private final OperationHandler supplyOperation = new SupplyOperation();

    @BeforeEach
    void setUp() {
        Storage.storage.put("banana", 20);
    }

    @Test
    void processOperation_supplyExistentFruit_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 30);
        supplyOperation.processOperation(fruitTransaction);
        int expectedAmountOfBanana = 50;
        int actual = Storage.storage.get("banana");
        assertEquals(expectedAmountOfBanana, actual);
    }

    @Test
    void processOperation_supplyNewFruit_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 100);
        supplyOperation.processOperation(fruitTransaction);
        Map<String, Integer> expected = Map.of(
                "banana", 20,
                "apple", 100);
        Map<String, Integer> actual = Storage.storage;
        assertEquals(expected, actual);
    }

    @Test
    void processOperation_supplyNegativeAmount_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", -30);
        assertThrows(RuntimeException.class, () ->
                supplyOperation.processOperation(fruitTransaction));
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}
