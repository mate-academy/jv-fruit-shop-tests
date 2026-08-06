package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private ReturnOperation returnOperation;

    @BeforeEach
    void setUp() {
        Storage.clearAll();
        returnOperation = new ReturnOperation();
    }

    @Test
    void process_fruitExists_addsReturnedQuantity() {
        Storage.putFruit("banana", 100);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana",
                50);

        returnOperation.process(transaction);
        assertEquals(150, Storage.getFruit("banana"));
    }

    @Test
    void process_fruitDoesNotExist_addsReturnedQuantity() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                "apple", 50);

        returnOperation.process(transaction);
        assertEquals(50, Storage.getFruit("apple"));
    }
}
