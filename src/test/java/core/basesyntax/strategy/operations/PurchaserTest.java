package core.basesyntax.strategy.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaserTest {
    private static OperationCompiler purchaser;

    @BeforeAll
    static void beforeAll() {
        purchaser = new Purchaser();
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.put("banana", 50);
    }

    @Test
    void doOperation_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class, () -> purchaser.doOperation(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,"banana", -1)));
        assertThrows(RuntimeException.class, () -> purchaser.doOperation(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,"banana", 51)));
    }

    @Test
    void doOperation_invalidFruitName_notOk() {
        assertThrows(RuntimeException.class, () -> purchaser.doOperation(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, null, 10)));
        assertThrows(RuntimeException.class, () -> purchaser.doOperation(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 10)));
    }

    @Test
    void doOperation_invalidOperation_notOk() {
        assertThrows(IllegalArgumentException.class, () -> purchaser.doOperation(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10)));
        assertThrows(IllegalArgumentException.class, () -> purchaser.doOperation(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10)));
        assertThrows(IllegalArgumentException.class, () -> purchaser.doOperation(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10)));
    }

    @Test
    void doOperation_rightData_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10);
        int balance = Storage.fruits.get("banana") - fruitTransaction.getQuantity();
        purchaser.doOperation(fruitTransaction);
        assertEquals(balance, Storage.fruits.get("banana"));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
