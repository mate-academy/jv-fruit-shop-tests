package core.basesyntax.strategy.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReturnerTest {
    private final OperationCompiler returner = new Returner();

    @Test
    void doOperation_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class, () -> returner.doOperation(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana", -1)));
    }

    @Test
    void doOperation_nullFruitName_notOk() {
        assertThrows(RuntimeException.class, () -> returner.doOperation(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, null, 10)));
    }

    @Test
    void doOperation_invalidOperation_notOk() {
        assertThrows(IllegalArgumentException.class, () -> returner.doOperation(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10)));
        assertThrows(IllegalArgumentException.class, () -> returner.doOperation(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10)));
        assertThrows(IllegalArgumentException.class, () -> returner.doOperation(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10)));
    }

    @Test
    void doOperation_rightData_ok() {
        Storage.fruits.put("banana", 50);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10);
        int fruitBalance = Storage.fruits.get(fruitTransaction.getFruit());
        returner.doOperation(fruitTransaction);
        assertEquals(fruitTransaction.getQuantity() + fruitBalance,
                Storage.fruits.get(fruitTransaction.getFruit()));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
